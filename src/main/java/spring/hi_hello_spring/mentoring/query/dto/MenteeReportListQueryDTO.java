package spring.hi_hello_spring.mentoring.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class MenteeReportListQueryDTO {

    private Long reportSeq;
    private int reportWeek;
    private LocalDateTime regDate;  // 보고서 생성 날짜
}
