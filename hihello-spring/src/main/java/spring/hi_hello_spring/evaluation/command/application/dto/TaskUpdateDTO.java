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
public class TaskUpdateDTO {

    private TaskType taskType;
    private Long templateSeq;
    private Long departmentSeq;
    private String taskTitle;
    private String taskContent;
    private String fileName;
    private List<EvalListCreateDTO> evalIndicators;

}
