package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;

public interface JpaEvalListRepository extends EvalListRepository, JpaRepository<EvalList, Long> {
}
