package spring.hi_hello_spring.mentoring.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.PlanningStatus;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class MentoringPlanUpdateDTO {

    private PlanningStatus planningStatus;
}
