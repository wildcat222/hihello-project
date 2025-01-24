package spring.hi_hello_spring.notify.command.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository{

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) { // emitter를 저장
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) { // 이벤트를 저장
        eventCache.put(eventCacheId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByEmployeeSeq(String employeeSeq) { // 해당 사원과 관련된 모든 이벤트를 찾음
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(employeeSeq))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByEmployeeSeq(String employeeSeq) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(employeeSeq))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String employeeSeq) { // emitter를 지움
        emitters.remove(employeeSeq);
    }

    @Override
    public void deleteAllEmitterStartWithId(String employeeSeq) { // 해당 사원과 관련된 모든 emitter를 지움
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(employeeSeq)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithId(String employeeSeq) { // 해당 사원과 관련된 모든 이벤트를 지움
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(employeeSeq)) {
                        eventCache.remove(key);
                    }
                }
        );
    }

}
