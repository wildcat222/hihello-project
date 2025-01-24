package spring.hi_hello_spring.mentoring.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MentoringGroupDTO {

    private Long mentorSeq;
    private Long menteeSeq;
}
