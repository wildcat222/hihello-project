package spring.hi_hello_spring.evaluation.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskCreateDTO {

    private Long departmentSeq;
    private TaskType taskType;
    private String taskTitle;
    private String templateTaskRound;
    private String taskContent;
    private String templateUrl;
    private List<EvalListCreateDTO> evalIndicators;


}