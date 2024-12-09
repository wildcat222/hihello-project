package spring.hi_hello_spring.group.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PeerReviewDetailQueryDTO {

    private String peerReviewListContent;
    private Integer peerReviewScore;
}
