package spring.hi_hello_spring.group.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultAllQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultDetailQueryDTO;

import java.util.List;

@Mapper
public interface PeerReviewResultMapper {
    List<PeerReviewResultAllQueryDTO> getPeerReviewResult(Long taskGroupSeq);

    PeerReviewResultDetailQueryDTO getPeerReviewResultDetail(Long employeeSeq);
}
