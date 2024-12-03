package spring.hi_hello_spring.group.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.group.query.dto.PeerReviewListAllQueryDTO;

import java.util.List;

@Mapper
public interface PeerReviewListMapper {
    List<PeerReviewListAllQueryDTO> findAllPeersReviewList();
}
