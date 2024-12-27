package spring.hi_hello_spring.notify.command.application.service;

import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;

import java.util.List;

public interface NotifyService {


    // 단일 리시버 (1명에게 알림 전송)
    public void send(Long senderSeq, Long receiverSeq, NotiType notiType, String url);

    // 다중 리시버 (여러명에게 알림 전송)
    public void send(Long senderSeq, List<Long> receivers, NotiType notiType, String url);

}
