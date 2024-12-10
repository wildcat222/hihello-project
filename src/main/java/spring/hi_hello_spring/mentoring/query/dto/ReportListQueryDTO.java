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
public class ReportListQueryDTO {

    private int reportWeek;
    private String menteeName;    // 멘티
    private LocalDateTime regDate;  // 보고서 생성 날짜
}
