package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskMenteeDetailQueryDTO {

    private Long taskSeq;
    private String taskType;
    private String taskTitle;
    private String taskUrl;
    private String taskContent;
    private Long templateSeq;
    private String templateTaskRound;
    private LocalDateTime templateEndAt;
}
