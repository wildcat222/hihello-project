package spring.hi_hello_spring.evaluation.query.dto;

import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskMentorDetailQueryDTO {

    private Long taskSeq;
    @Enumerated
    private TaskType taskType;
    private String taskTitle;
    private Long templateSeq;
    private String taskContent;
    private String templateTaskRound;
    private LocalDateTime templateEndAt;
    private Long fileSeq;
    private String fileName;
    private String fileUrl;
    private List<EvalListQueryDTO> evalList;
}
