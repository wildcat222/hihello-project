package spring.hi_hello_spring.notify.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.Notify;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotifyDTO {

    private Long notiSeq;         // 알림 고유 Seq
    private Long employeeSeq;     // 수신자 Seq
    private Long senderSeq;       // 발신자 Seq
    private NotiType notiType;     // 알림 타입
    private String notiContent;   // 알림 내용
    private String notiUrl;       // 알림 URL
    private boolean alarmReadStatus; // 읽음 상태
    private LocalDate createdAt; // 생성 시간
    private LocalDate updatedAt; // 수정 시간

    // Notify 엔티티를 입력받는 생성자 추가
    public NotifyDTO(Notify notify) {
        this.notiSeq = notify.getNotiSeq();
        this.employeeSeq = notify.getEmployeeSeq();
        this.notiContent = notify.getNotiContent();
        this.notiUrl = notify.getNotiUrl();
        this.alarmReadStatus = notify.isAlarmReadStatus();
        this.createdAt = notify.getRegDate().toLocalDate();
    }

    // Notify 엔티티를 DTO로 변환하는 정적 메서드
    public static NotifyDTO createResponse(Notify notify, Long senderSeq) {
        return NotifyDTO.builder()
                .notiSeq(notify.getNotiSeq())
                .employeeSeq(notify.getEmployeeSeq())
                .notiType(notify.getNotiType())
                .senderSeq(senderSeq)
                .notiContent(notify.getNotiContent())
                .notiUrl(notify.getNotiUrl())
                .alarmReadStatus(notify.isAlarmReadStatus())
                .createdAt(notify.getRegDate().toLocalDate())
                .build();
    }
}
