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
    private String templateTitle;
    private String taskType;
    private Long templateSeq;
    private String taskContent;
    private String taskUrl;
    private Long departmentSeq;
    private String departmentName;
    private String templateTaskRound;
    private LocalDateTime templateEndAt;
}
