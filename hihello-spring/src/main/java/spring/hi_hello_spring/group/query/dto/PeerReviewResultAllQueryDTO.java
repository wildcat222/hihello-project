package spring.hi_hello_spring.group.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PeerReviewResultAllQueryDTO {

    private Long employeeSeq;
    private String employeeNum;
    private String departmentName;
    private String reviewerName;
    private String revieweeName;
    private int peerReviewScoreListSum;
    private int peerReviewScoreSum;
}
