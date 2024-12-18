package spring.hi_hello_spring.evaluation.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SubmittedTaskQueryDTO {

    private Long taskSeq;
    private String taskTitle;
    private String taskFileName;
    private String taskFileUrl;
    private String taskContent;
    private Long taskSubmitSeq;
    private String taskSubmitContent;
    private String taskSubmitFileName;
    private String taskSubmitFileUrl;
    private LocalDateTime taskSubmitDate;
}