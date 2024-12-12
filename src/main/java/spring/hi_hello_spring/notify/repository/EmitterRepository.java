package spring.hi_hello_spring.notify.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String eventCacheId, Object event);

    Map<String, SseEmitter> findAllEmitterStartWithByEmployeeSeq(String EmployeeSeq);

    Map<String, Object> findAllEventCacheStartWithByEmployeeSeq(String EmployeeSeq);

    void deleteById(String EmployeeSeq);

    void deleteAllEmitterStartWithId(String EmployeeSeq);

    void deleteAllEventCacheStartWithId(String EmployeeSeq);


}
