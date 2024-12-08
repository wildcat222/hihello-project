package spring.hi_hello_spring.mentoring.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {

    Mentoring findByMentoringSeq(Long mentoringSeq);
}
