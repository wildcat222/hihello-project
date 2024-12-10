package spring.hi_hello_spring.chatting.command.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ChatRequestMessage {

    private Long userCode;
    private String message;
    private LocalDateTime createdAt;

    @JsonCreator
    public ChatRequestMessage(
            @JsonProperty("userCode") Long userCode,
            @JsonProperty("message") String message,
            @JsonProperty("createdAt") LocalDateTime createdAt) {
        this.userCode = userCode;
        this.message = message;
        this.createdAt = createdAt;
    }
}
