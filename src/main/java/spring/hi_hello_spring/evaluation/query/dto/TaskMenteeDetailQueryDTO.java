package spring.hi_hello_spring.evaluation.query.dto;

import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskMenteeDetailQueryDTO {

    private Long taskSeq;
    @Enumerated
    private TaskType taskType;
    private String taskTitle;
    private String taskUrl;
    private Long templateSeq;
    private String taskContent;
    private String templateTaskRound;
    private LocalDateTime templateEndAt;
}
