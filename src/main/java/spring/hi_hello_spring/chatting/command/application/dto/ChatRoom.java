package spring.hi_hello_spring.chatting.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {

    private String roomId;
    private Long mentorSeq;
    private Long menteeSeq;
    private List<Long> memberSeqs;
}