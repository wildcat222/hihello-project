package spring.hi_hello_spring.onboarding.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.CheckListStatusRepository;

public interface JpaChecklistStatusRepository extends CheckListStatusRepository, JpaRepository<CheckListStatus, Long> {
}
