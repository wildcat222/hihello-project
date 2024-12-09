package spring.hi_hello_spring.group.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultAllQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultDetailQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewDetailQueryDTO;
import spring.hi_hello_spring.group.query.mapper.PeerReviewResultMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeerReviewResultQueryService {

    private final PeerReviewResultMapper peerReviewResultMapper;

    /* 동료 평가 결과 리스트 조회 */
    public List<PeerReviewResultAllQueryDTO> getAllPeerReviewResult(Long taskGroupSeq) {

        return peerReviewResultMapper.getPeerReviewResult(taskGroupSeq);
    }

    /* 동료 평가 결과 상세 조회 */
    public PeerReviewResultDetailQueryDTO getAllPeerReviewResultDetail(Long employeeSeq) {

        PeerReviewResultDetailQueryDTO detail = peerReviewResultMapper.getPeerReviewResultDetail(employeeSeq);

        int totalScore = detail.getPeerReviewDetails()
                .stream()
                .mapToInt(PeerReviewDetailQueryDTO::getPeerReviewScore)
                .sum();

        detail.setPeerReviewScoreSum(totalScore);

        return detail; 
    }
}
