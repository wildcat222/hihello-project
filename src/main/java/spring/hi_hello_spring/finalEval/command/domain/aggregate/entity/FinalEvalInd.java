package spring.hi_hello_spring.finalEval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "final_eval_ind")
@NoArgsConstructor
@Getter
public class FinalEvalInd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finalEvalIndSeq;

    private String finalEvalIndName;

    private int finalEvalIndWeight;
}
