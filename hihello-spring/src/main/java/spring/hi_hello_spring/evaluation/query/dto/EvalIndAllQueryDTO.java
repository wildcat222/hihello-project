package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EvalIndAllQueryDTO {

    private Long evalIndSeq;
    private String evalIndContent;
    private int evalIndScore;
}
