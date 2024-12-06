package spring.hi_hello_spring.wiki.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WikiHistoryListQueryDTO {

    private Long employeeSeq;
    private String employeeNum;
    private String employeeName;
    private Long wikiModContentSeq;
    private LocalDateTime regDate;
}