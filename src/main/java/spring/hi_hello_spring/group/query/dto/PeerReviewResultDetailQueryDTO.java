package spring.hi_hello_spring.group.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PeerReviewResultDetailQueryDTO {

    private List<String> peerReviewListContent;  // 평가 항목들
    private List<Integer> peerReviewScore;  // 평가 점수들
    private int peerReviewScoreSum; // 동료 평가 점수 총점


}
