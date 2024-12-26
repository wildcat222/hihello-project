package spring.hi_hello_spring.notify.command.domain.repository;

import org.apache.el.stream.Optional;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.Notify;

import java.util.List;

public interface NotifyRepository {

    List<Notify> findAllByEmployeeSeq(Long employeeSeq);

    List<Notify> findNotReadNotifyByEmployeeSeq(Long employeeSeq);
}
