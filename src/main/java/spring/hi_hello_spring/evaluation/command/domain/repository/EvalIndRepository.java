package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.application.dto.EvalIndCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;
import java.util.Optional;

public interface EvalIndRepository {

    EvalInd save(EvalInd evalInd);

    boolean existsById(Long evalIndSeq);

    void deleteById(Long evalIndSeq);

    Optional<EvalInd> findById(Long evalIndSeq);
}
