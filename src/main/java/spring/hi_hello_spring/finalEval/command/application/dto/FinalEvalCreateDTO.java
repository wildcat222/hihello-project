package spring.hi_hello_spring.finalEval.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;

@Getter
@Setter
@ToString
public class FinalEvalCreateDTO {

    private Long evalIndSeq;
    private Long finalEvalIndSeq;

    // evalIndSeq와 finalEvalIndSeq 중 하나만 값이 있어야 함에 대한 유효성 검증
    public void validate() {
        if ((this.getFinalEvalIndSeq() == null && this.getEvalIndSeq() == null)) {
            throw new CustomException(ErrorCodeType.INVALID_REQUEST);
        }
    }
}