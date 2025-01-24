package spring.hi_hello_spring.group.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.group.command.application.dto.PeerReviewListCreateDTO;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReviewList;
import spring.hi_hello_spring.group.command.domain.repository.PeerReviewListRepository;

@Service
@RequiredArgsConstructor
public class PeerReviewListService {

    private final PeerReviewListRepository peerReviewListRepository;
    private final ModelMapper modelMapper;

    /* 동료 평가 지표 등록 */
    @Transactional
    public PeerReviewListCreateDTO createPeerReviewList(PeerReviewListCreateDTO peerReviewListCreateDTO) {

        PeerReviewList peerReviewList = modelMapper.map(peerReviewListCreateDTO, PeerReviewList.class);
        peerReviewListRepository.save(peerReviewList);

        return peerReviewListCreateDTO;
    }

    /* 동료 평가 지표 삭제 */
    @Transactional
    public boolean deletePeerReviewList(Long peerReviewListSeq) {

        if(peerReviewListRepository.existsById(peerReviewListSeq)) {
            peerReviewListRepository.deleteById(peerReviewListSeq);
            return true;
        }else{
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}
