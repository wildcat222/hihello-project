package spring.hi_hello_spring.group.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReview;
import spring.hi_hello_spring.group.command.domain.repository.PeerReviewRepository;

public interface JpaPeerReviewRepository extends PeerReviewRepository, JpaRepository<PeerReview, Long> {
}