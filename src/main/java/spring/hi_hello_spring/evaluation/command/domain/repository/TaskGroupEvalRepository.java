package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReview;
import java.util.List;

public interface TaskGroupEvalRepository {

    PeerReview save(PeerReview peerReview);

    Iterable saveAll(Iterable peerReviews);
}
