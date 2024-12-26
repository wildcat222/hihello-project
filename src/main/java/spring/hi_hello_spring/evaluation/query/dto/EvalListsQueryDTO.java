package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvalListsQueryDTO {

    private Long evalIndSeq;
    private String evalIndContent;
    private Long evalListSeq;
    private String evalListContent;
    private Double evalListScore;
}