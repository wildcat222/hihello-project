package spring.hi_hello_spring.finalEval.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEval;
import spring.hi_hello_spring.finalEval.command.domain.repository.FinalEvalRepository;

public interface JpaFinalEvalRepository extends FinalEvalRepository, JpaRepository<FinalEval, Long> {
}