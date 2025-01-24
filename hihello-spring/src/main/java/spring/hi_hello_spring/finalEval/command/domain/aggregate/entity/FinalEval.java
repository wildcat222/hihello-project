package spring.hi_hello_spring.finalEval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "final_eval")
@NoArgsConstructor
@Getter
public class FinalEval extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finalEvalSeq;

    private Long employeeSeq;

    private Long evalIndSeq;

    private Long finalEvalIndSeq;

    private double employeeScore;

    public void updateEmployeeScore(double finalScore) {
        this.employeeScore = finalScore;
    }

    public void updateEmployeeSeq(Long employeeSeq) {
        this.employeeSeq = employeeSeq;
    }

    @Builder
    public FinalEval(Long employeeSeq, Long evalIndSeq, Long finalEvalIndSeq, double employeeScore) {
        this.employeeSeq = employeeSeq;
        this.evalIndSeq = evalIndSeq;
        this.finalEvalIndSeq = finalEvalIndSeq;
        this.employeeScore = employeeScore;
    }
}