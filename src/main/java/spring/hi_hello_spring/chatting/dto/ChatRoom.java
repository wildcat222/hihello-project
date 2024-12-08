package spring.hi_hello_spring.chatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {

    private Long roomId;
    private Long mentorSeq;
    private Long menteeSeq;
}