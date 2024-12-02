package spring.hi_hello_spring.finalEval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "finalEval")
@NoArgsConstructor
@Getter
public class finalEval extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long finalEvalSeq;

    private Long employeeSeq;

    private Long evalIndSeq;

    private Long finalEvalIndSeq;

    private Long employeeScore;
}
