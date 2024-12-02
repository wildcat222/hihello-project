package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_eval")
@NoArgsConstructor
@Getter
public class EvalList extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long evalListSeq;

    private Long taskSeq;

    private Long evalIndSeq;

    private String evalListContent;

    private int evalListScore;
}
