package spring.hi_hello_spring.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.notify.entity.Notify;

import java.util.List;

public interface NotifyRepository extends JpaRepository<Notify, Long> {

    List<Notify> findAllByEmployeeSeq(Long employeeSeq);

    List<Notify> findNotReadNotifyByEmployeeSeq(Long employeeSeq);
}
