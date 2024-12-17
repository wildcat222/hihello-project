package spring.hi_hello_spring.finalEval.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FinalEvalDetailQueryDTO {

    private Long fileSeq;
    private String profileImgUrl;
    private Long employeeSeq;
    private String employeeName;
    private String employeePhone;
    private String employeeEmail;
    private String departmentName;
    private String employeeNum;
    private List<FinalEvalResultQueryDTO> finalEvalResults;
}