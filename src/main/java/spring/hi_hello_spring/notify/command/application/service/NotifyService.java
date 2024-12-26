package spring.hi_hello_spring.notify.command.application.service;

import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;

import java.util.List;

public interface NotifyService {


    // 단일 리시버 (게시글에 대한 댓글 발생시 받는 유저는 게시글 작성자 한 명)
    public void send(Employee sender, Employee receiver, NotiType notiType, String url);

    // 다중 리시버
    // 케이스 공유시 팔로워들에게 알림 발생
    // 게시글 작성시 팔로워들에게 알림 발생
    public void send(Employee sender, List<Employee> receivers, NotiType notiType, String url);

}
