package spring.hi_hello_spring.mentoring.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class WriteFeedbackDTO {

    private String reportFeedbackContent;
}
