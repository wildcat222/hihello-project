package spring.hi_hello_spring.chatting.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResponseMessage {
    private Long userCode;
    private String message;
    private LocalDateTime createdAt;

    public ChatResponseMessage(Long userCode, String message, LocalDateTime createdAt) {
        this.userCode = userCode;
        this.message = message;
        this.createdAt = createdAt;
    }
}
