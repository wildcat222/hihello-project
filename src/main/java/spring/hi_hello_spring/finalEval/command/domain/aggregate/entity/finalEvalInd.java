package spring.hi_hello_spring.finalEval.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "finalEvalInd")
@NoArgsConstructor
@Getter
public class finalEvalInd {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long finalEvalIndSeq;

    private String finalEvalIndName;

    private int finalEvalIndWeight;
}
