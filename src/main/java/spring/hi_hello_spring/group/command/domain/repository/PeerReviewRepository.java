package spring.hi_hello_spring.group.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeerReviewRepository {

    @Query("SELECT SUM(prl.peerReviewListScore) " +
            "FROM PeerReview pr " +
            "JOIN PeerReviewList prl ON pr.peerReviewListSeq = prl.peerReviewListSeq " +
            "WHERE pr.revieweeSeq = :revieweeSeq")
    Integer findTotalPeerReviewPerfectScoreByRevieweeSeq(@Param("revieweeSeq") Long revieweeSeq);

    @Query("SELECT SUM(peerReviewScore) FROM PeerReview WHERE revieweeSeq = :revieweeSeq ")
    Integer sumPeerReviewScoreByRevieweeSeq(@Param("revieweeSeq") Long revieweeSeq);
}