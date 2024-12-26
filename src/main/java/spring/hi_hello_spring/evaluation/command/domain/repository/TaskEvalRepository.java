package spring.hi_hello_spring.evaluation.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskEval;

public interface TaskEvalRepository {
    
    TaskEval save(TaskEval taskEval);

    boolean existsByTaskSubmitSeqAndEvalListSeq(Long taskSubmitSeq, Long evalListSeq);

    // 그룹 과제일 경우 해당 평가 지표에 대한 사원 점수 계산
    @Query("SELECT SUM(te.taskScore) " +
            "FROM TaskEval te " +
            "JOIN TaskSubmit ts ON te.taskSubmitSeq = ts.taskSubmitSeq " +
            "JOIN Task t ON ts.taskSeq = t.taskSeq " +
            "JOIN TaskGroup tg ON t.taskSeq = tg.taskSeq " +
            "JOIN GroupMember gm ON tg.taskGroupSeq = gm.taskGroupSeq " +
            "JOIN EvalList el ON el.taskSeq = t.taskSeq " +
            "JOIN EvalInd ei ON ei.evalIndSeq = el.evalIndSeq " +
            "WHERE t.taskType = 'GROUP' AND gm.employeeSeq = :employeeSeq AND ei.evalIndSeq = :evalIndSeq ")
    Integer sumGroupTaskScoreByTaskSubmitSeqAndEvalIndSeq(@Param("employeeSeq") Long employeeSeq, @Param("evalIndSeq")Long evalIndSeq);

    // 개인 과제일 경우 해당 평가 지표에 대한 사원 점수 계산
    @Query("SELECT SUM(te.taskScore) " +
            "FROM TaskEval te " +
            "JOIN TaskSubmit ts ON te.taskSubmitSeq = ts.taskSubmitSeq " +
            "JOIN Task t ON ts.taskSeq = t.taskSeq " +
            "JOIN EvalList el ON te.evalListSeq = el.evalListSeq " +
            "JOIN EvalInd ei ON ei.evalIndSeq = el.evalIndSeq " +
            "WHERE t.taskType = 'PERSONAL' AND ts.employeeSeq = :employeeSeq AND ei.evalIndSeq = :evalIndSeq")
    Integer sumTaskScoreByEmployeeSeqAndEvalIndSeq(@Param("employeeSeq") Long employeeSeq, @Param("evalIndSeq")Long evalIndSeq);
}