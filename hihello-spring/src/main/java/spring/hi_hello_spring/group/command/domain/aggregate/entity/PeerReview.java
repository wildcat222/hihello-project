package spring.hi_hello_spring.group.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "peer_review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PeerReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peerReviewSeq;

    private Long peerReviewListSeq;

    private Long reviewerSeq;

    private Long revieweeSeq;

    private Integer peerReviewScore;
}
