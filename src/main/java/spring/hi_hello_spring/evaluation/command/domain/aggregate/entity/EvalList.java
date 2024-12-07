package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "eval_list")
@NoArgsConstructor
@Getter
public class EvalList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evalListSeq;

    private Long taskSeq;

    private Long evalIndSeq;

    private String evalListContent;

    private Integer evalListScore;

    public void updateEvalIndSeq(Long evalIndSeq) {
        this.evalIndSeq = evalIndSeq;
    }

    public void updateTaskSeq(Long taskSeq) {
        this.taskSeq = taskSeq;
    }
}
