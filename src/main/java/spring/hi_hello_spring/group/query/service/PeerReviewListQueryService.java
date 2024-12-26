package spring.hi_hello_spring.group.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.group.query.dto.PeerReviewListAllQueryDTO;
import spring.hi_hello_spring.group.query.mapper.PeerReviewListMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeerReviewListQueryService {

    private final PeerReviewListMapper peerReviewListMapper;

    /* 동료 평가 지표 리스트 조회 */
    public List<PeerReviewListAllQueryDTO> getAllPeersReviewList() {

        return peerReviewListMapper.findAllPeersReviewList();
    }
}
