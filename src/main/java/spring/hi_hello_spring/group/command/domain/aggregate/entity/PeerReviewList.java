package spring.hi_hello_spring.group.command.domain.aggregate.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "peer_review_list")
@NoArgsConstructor
@Getter
public class PeerReviewList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long peerReviewListSeq;

    private String peerReviewListContent;

    private int peerReviewScore;
}
