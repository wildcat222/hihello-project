package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskGroupPartnerQueryDTO {
    private int taskGroupSeq;
    private int groupMemberSeq;
    private int employeeSeq;
    private String employeeName;
}
