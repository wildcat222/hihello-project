package spring.hi_hello_spring.onboarding.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Checklist;
import spring.hi_hello_spring.onboarding.command.domain.repository.ChecklistRepository;

public interface JpaChecklistRepository extends ChecklistRepository, JpaRepository<Checklist, Long> {
}
