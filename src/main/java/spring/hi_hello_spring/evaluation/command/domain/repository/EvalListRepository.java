package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;

import java.util.List;

public interface EvalListRepository {

    Iterable saveAll(Iterable evalIndicators);

    EvalList save(EvalList evalList);

    List<EvalList> findByEvalIndSeq(Long evalIndSeq);
}