package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalIndRepository;

public interface JpaEvalIndRepository extends EvalIndRepository, JpaRepository<EvalInd, Long> {
}
