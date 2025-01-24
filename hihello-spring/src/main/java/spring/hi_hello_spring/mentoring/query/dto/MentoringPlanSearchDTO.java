package spring.hi_hello_spring.mentoring.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.PlanningStatus;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MentoringPlanSearchDTO {

    private String employeeName;
    private String planningName;
    private PlanningStatus planningStatus;
    private LocalDate regDate;
    private String fileUrl;
}
