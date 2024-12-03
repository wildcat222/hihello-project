package spring.hi_hello_spring.group.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PeerReviewListCreateDTO {

    private String peerReviewListContent;
    private int peerReviewScore;
}
