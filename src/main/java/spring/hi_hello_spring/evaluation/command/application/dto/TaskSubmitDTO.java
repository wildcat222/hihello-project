package spring.hi_hello_spring.evaluation.command.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskSubmitDTO {

    private String taskSubmitContent;
    private String fileName;
    private String taskAttachedUrl;

}
