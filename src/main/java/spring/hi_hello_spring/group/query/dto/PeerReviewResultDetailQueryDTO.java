package spring.hi_hello_spring.group.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
public class PeerReviewResultDetailQueryDTO {

    private List<PeerReviewDetailQueryDTO> peerReviewDetails;
    private int peerReviewScoreSum; // 동료 평가 점수 총점

}
