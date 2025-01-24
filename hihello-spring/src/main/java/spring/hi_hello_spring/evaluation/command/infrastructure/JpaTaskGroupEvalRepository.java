package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskGroupEvalRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReview;

public interface JpaTaskGroupEvalRepository extends TaskGroupEvalRepository, JpaRepository<PeerReview, Long> {
}
