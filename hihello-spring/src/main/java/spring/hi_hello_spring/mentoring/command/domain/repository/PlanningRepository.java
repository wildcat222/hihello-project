package spring.hi_hello_spring.mentoring.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Planning;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
}
