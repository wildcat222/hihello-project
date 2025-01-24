package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;
import java.util.List;
import java.util.Optional;

public interface EvalIndRepository {

    EvalInd save(EvalInd evalInd);

    boolean existsById(Long evalIndSeq);

    void deleteById(Long evalIndSeq);

    Optional<EvalInd> findById(Long evalIndSeq);

    Long countBy();

    List<EvalInd> findAll();
}
