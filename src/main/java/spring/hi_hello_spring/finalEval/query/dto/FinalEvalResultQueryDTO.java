package spring.hi_hello_spring.finalEval.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FinalEvalResultQueryDTO {

    private Long finalEvalIndSeq;
    private String finalEvalIndName;
    private Double finalEvalScore;
}