package spring.hi_hello_spring.finalEval.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEvalInd;
import spring.hi_hello_spring.finalEval.command.domain.repository.FinalEvalIndRepository;

public interface JpaFinalEvalIndRepository extends FinalEvalIndRepository, JpaRepository<FinalEvalInd, Long> {
}