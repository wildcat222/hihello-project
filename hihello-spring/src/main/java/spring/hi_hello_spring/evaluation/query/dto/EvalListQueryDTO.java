package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EvalListQueryDTO {

    private String evalListContent;
    private Integer evalListScore;
}
