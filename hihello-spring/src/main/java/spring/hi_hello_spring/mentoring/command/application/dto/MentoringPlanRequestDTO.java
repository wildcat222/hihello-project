package spring.hi_hello_spring.mentoring.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MentoringPlanRequestDTO {

    private Long employeeSeq;
    private String planningName;
    private String planningContent;
    private String fileName;
}
