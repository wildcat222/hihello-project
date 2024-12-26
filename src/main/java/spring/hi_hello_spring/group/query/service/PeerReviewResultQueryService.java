package spring.hi_hello_spring.group.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultAllQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultDetailQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewDetailQueryDTO;
import spring.hi_hello_spring.group.query.mapper.PeerReviewResultMapper;

import java.util.List;
import java.util.Objects;

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
        // Mapper에서 다수의 PeerReviewDetailQueryDTO 가져오기
        List<PeerReviewDetailQueryDTO> details = peerReviewResultMapper.getPeerReviewResultDetail(employeeSeq);

        // 총합 점수를 계산
        int totalScore = details.stream()
                .filter(Objects::nonNull) // null 값 방지
                .mapToInt(PeerReviewDetailQueryDTO::getPeerReviewScore)
                .sum();

        // PeerReviewResultDetailQueryDTO에 데이터 세팅
        PeerReviewResultDetailQueryDTO resultDetail = new PeerReviewResultDetailQueryDTO();
        resultDetail.setPeerReviewDetails(details);
        resultDetail.setPeerReviewScoreSum(totalScore);

        return resultDetail;
    }
}
