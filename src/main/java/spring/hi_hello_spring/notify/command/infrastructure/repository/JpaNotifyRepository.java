package spring.hi_hello_spring.notify.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.Notify;
import spring.hi_hello_spring.notify.command.domain.repository.NotifyRepository;

import java.util.List;

public interface JpaNotifyRepository extends NotifyRepository, JpaRepository<Notify, Long> {
}
