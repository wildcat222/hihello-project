package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskSearchQueryDTO {

    private int taskSeq;
    private int departmentSeq;
    private String departmentName;
    private int templateSeq;
    private String templateTaskRound;
    private String taskTitle;
    private String taskContent;

}
