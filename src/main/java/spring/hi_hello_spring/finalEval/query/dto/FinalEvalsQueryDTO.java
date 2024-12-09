package spring.hi_hello_spring.finalEval.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FinalEvalsQueryDTO {

    private Long employeeSeq;
    private String employeeNum;
    private String employeeName;
    private Long departmentSeq;
    private String departmentName;
    private Double totalFinalEvalScore;
}