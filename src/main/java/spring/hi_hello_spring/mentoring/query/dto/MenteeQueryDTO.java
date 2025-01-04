package spring.hi_hello_spring.mentoring.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MenteeQueryDTO {

    private Long mentorSeq;
    private Long menteeSeq;
}
