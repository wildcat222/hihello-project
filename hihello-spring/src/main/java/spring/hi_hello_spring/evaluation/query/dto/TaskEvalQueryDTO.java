package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskEvalQueryDTO {

    private Long evalListSeq;
    private String evalListContent;
    private int evalListScore;
    private Long evalIndSeq;
    private String evalIndContent;
}
