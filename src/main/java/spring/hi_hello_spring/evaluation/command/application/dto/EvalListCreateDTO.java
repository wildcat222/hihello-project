package spring.hi_hello_spring.evaluation.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EvalListCreateDTO {

    private String evalListContent;
    private Integer evalListScore;
    private Long evalIndSeq;
}