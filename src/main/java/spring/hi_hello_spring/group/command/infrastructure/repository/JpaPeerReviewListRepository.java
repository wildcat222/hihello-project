package spring.hi_hello_spring.group.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReviewList;
import spring.hi_hello_spring.group.command.domain.repository.PeerReviewListRepository;

public interface JpaPeerReviewListRepository extends PeerReviewListRepository, JpaRepository<PeerReviewList, Long> {
}
