package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_eval")
@NoArgsConstructor
@Getter
public class EvalInd extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long evalIndSeq;

    private String evalIndContent;

    private int evalIndScore;
}
