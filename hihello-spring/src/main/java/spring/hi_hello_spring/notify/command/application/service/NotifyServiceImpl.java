package spring.hi_hello_spring.notify.command.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.Notify;
import spring.hi_hello_spring.notify.command.domain.repository.EmitterRepositoryImpl;
import spring.hi_hello_spring.notify.command.infrastructure.repository.JpaNotifyRepository;
import spring.hi_hello_spring.notify.command.application.dto.NotifyDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    // SSE 연결 지속 시간 설정 (1시간)
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final JpaNotifyRepository notifyRepository;
    private final EmitterRepositoryImpl emitterRepository;

    public SseEmitter subscribe(String employeeSeq, String lastEventId) {

        String emitterId = makeTimeIncludeId(employeeSeq); // (1-2)
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT)); // (1-3)


        log.info("emitterId: {}",emitterId);
        log.info("emitter: {}",emitter.toString());

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> log.error("SSE connection error for {}: {}", employeeSeq, e.getMessage()));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(employeeSeq);
        sendNotification(emitter, eventId, emitterId,
                Map.of(
                        "type", "connection",
                        "message", "EventStream Created. [employeeSeq = " + employeeSeq + "]"
                )
        );

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, employeeSeq, emitterId, emitter);
        }

        return emitter;
    }

    // 아이디 생셩
    private String makeTimeIncludeId(String employeeSeq) {
        return employeeSeq + "_" + System.currentTimeMillis();
    }

    // 알림 보내기
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
//                    .name("sse")
                    .data(data));
        } catch (IOException e) {
            log.error("SSE 전송 실패: eventId={}, emitterId={}", eventId, emitterId, e);
            emitterRepository.saveEventCache(emitterId, data); // 캐시 저장
            emitterRepository.deleteById(emitterId);
        }
    }

    // 미수신 알림이 있는 경우
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    // 마지막 데이터들 넘겨보내기
    private void sendLostData(String lastEventId, String employeeSeq, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByEmployeeSeq(employeeSeq);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    // 받는 사람이 한명인 경우
    @Transactional
    @Override
    public void send(Long senderSeq, Long receiverSeq, NotiType notiType, String url) {

        sendToReceivers(senderSeq, List.of(receiverSeq), notiType, url);
    }

    // 받는 사람이 여러명인 경우
    @Transactional
    @Override
    public void send(Long senderSeq, List<Long> receivers, NotiType notiType, String url) {

        sendToReceivers(senderSeq, receivers, notiType, url);
    }

    public void sendToReceivers(Long senderSeq, List<Long> receivers, NotiType notiType, String url) {

        // 알림 생성 및 저장
        receivers.forEach(receiver -> {
            // senderSeq 로 수정
            String senderSeqStr = String.valueOf(senderSeq);
            Notify notification = notifyRepository.save(
                    createNotification(receiver, notiType, notiType.getMessage(), url)
            );

            log.debug("Created notification: {}", notification);

            String receiverSeq = String.valueOf(receiver);
            String eventId = makeTimeIncludeId(receiverSeq);
            Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByEmployeeSeq(receiverSeq);

            emitters.forEach((key, emitter) -> {
                emitterRepository.saveEventCache(key, notification);
                Long employeeSeq = Long.parseLong(senderSeqStr);
//                sendNotification(emitter, eventId, key, NotifyDTO.createResponse(notification, employeeSeq));
                ObjectMapper objectMapper = new ObjectMapper(); // Spring Boot에서는 주입받아 사용하는 것을 권장
                objectMapper.registerModule(new JavaTimeModule());
                String jsonData = null;
                try {
                    // 알림 응답 DTO로 매핑하도록 수정해야함
                    jsonData = objectMapper.writeValueAsString(NotifyDTO.createResponse(notification, employeeSeq));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                sendNotification(emitter, eventId, key, jsonData);
            });
        });
    }

    public Notify createNotification(Long receiverSeq, NotiType notificationType, String content, String url) {

        return Notify.builder()
                .employeeSeq(receiverSeq)
                .notiType(notificationType)
                .notiContent(content)
                .notiUrl(url)
                .alarmReadStatus(false)
                .build();
    }

    // 알림 단일 읽음
    @Transactional
    public void readNotify(Long employeeSeq, Long notiSeq){

        Notify notify = notifyRepository.findById(notiSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        if (Objects.equals(notify.getEmployeeSeq(), employeeSeq)) {
            // 읽음 처리
            notify.notiRead();
        }
    }

    // 알림 단일 삭제
    @Transactional
    public void deleteNotify(Long employeeSeq, Long notiSeq){

        Notify notify = notifyRepository.findById(notiSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        if (Objects.equals(notify.getEmployeeSeq(), employeeSeq)) {
            // 삭제 처리
            notifyRepository.delete(notify);
        }
    }

    // 알림 전체 읽음
    @Transactional
    public void readAllNotify(Long employeeSeq){

        List<Notify> notReadNotifyByUserId =
                notifyRepository.findNotReadNotifyByEmployeeSeq(employeeSeq);

        notReadNotifyByUserId
                .forEach(Notify::notiRead);
    }

    // 알림 전체 삭제
    @Transactional
    public void deleteAllNotify(Long employeeSeq){

        List<Notify> allByEmployeeSeq = notifyRepository.findAllByEmployeeSeq(employeeSeq);

        notifyRepository.deleteAll(allByEmployeeSeq);
    }

}
