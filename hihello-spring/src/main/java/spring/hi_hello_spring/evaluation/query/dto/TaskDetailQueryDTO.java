package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;

import java.util.List;

@Getter
@Setter
@ToString
public class TaskDetailQueryDTO {
    private Long taskSeq;
    private Long templateSeq;
    private Long departmentSeq;
    private Long evalListSeq;
    private String taskTitle;
    private String departmentName;
    private String templateTaskRound;
    private String templateUrlName;
    private String templateType;
    private TaskType taskType;
    private String taskContent;
    private Long fileSeq;
    private String fileName;
    private String fileUrl;
    private List<TaskEvalQueryDTO> evalList;
}
