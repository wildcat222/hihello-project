package spring.hi_hello_spring.evaluation.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EvalGroupCreateDTO {

    private Long peerReviewListSeq;
    private Long revieweeSeq;
    private Integer peerReviewScore;

}