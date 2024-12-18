package spring.hi_hello_spring.onboarding.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckListUpdateDTO {

    private Long checklistStatusSeq;
    private Long checklistSeq;
    private Boolean listCheckedStatus;

}
