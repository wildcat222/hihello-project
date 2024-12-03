package spring.hi_hello_spring.group.command.domain.repository;

import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReviewList;

public interface PeerReviewListRepository {

    PeerReviewList save(PeerReviewList peerReviewList);
}
