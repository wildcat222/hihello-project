package spring.hi_hello_spring.finalEval.command.domain.repository;

import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEval;

import java.util.Optional;

public interface FinalEvalRepository {

    boolean existsById(Long finalEvalSeq);

    Optional<FinalEval> findById(Long finalEvalIndSeq);

    FinalEval save(FinalEval finalEval);

    boolean existsByEmployeeSeqAndEvalIndSeq(Long employeeSeq, Long evalIndSeq);

    boolean existsByEmployeeSeqAndFinalEvalIndSeq(Long employeeSeq, Long finalEvalIndSeq);
}