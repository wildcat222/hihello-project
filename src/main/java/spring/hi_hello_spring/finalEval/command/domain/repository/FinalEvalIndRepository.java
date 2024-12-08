package spring.hi_hello_spring.finalEval.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEvalInd;

import java.util.Optional;

public interface FinalEvalIndRepository {

    Optional<FinalEvalInd> findById(Long finalEvalIndSeq);

    @Query("SELECT SUM(finalEvalIndWeight) FROM FinalEvalInd")
    Integer sumFinalEvalIndWeight();
}