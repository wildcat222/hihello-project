package spring.hi_hello_spring.evaluation.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvalListCreateDTO {

    private String evalListContent;
    private int evalListScore;
}