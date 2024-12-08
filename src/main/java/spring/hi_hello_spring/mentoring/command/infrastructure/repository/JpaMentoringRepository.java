package spring.hi_hello_spring.mentoring.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;

public interface JpaMentoringRepository extends MentoringRepository, JpaRepository<Mentoring, Long> {
}