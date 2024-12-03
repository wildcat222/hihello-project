package spring.hi_hello_spring.group.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PeerReviewListAllQueryDTO {

    private Long peerReviewListSeq;
    private String peerReviewListContent;
    private int peerReviewListScore;
}
