package spring.hi_hello_spring.mentoring.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ResMentoringReportDTO {

    private Long reportSeq;
    private String menteeName;
    private String mentorName;
    private int reportWeek;
    private String reportContent;
    private String reportFeeling;
    private String reportFeedbackContent;

}
