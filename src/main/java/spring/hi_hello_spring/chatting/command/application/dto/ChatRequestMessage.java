package spring.hi_hello_spring.chatting.command.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ChatRequestMessage {
    private String roomId;
    private Long userCode;
    private String message;
    private LocalDateTime createdAt;

    @JsonCreator
    public ChatRequestMessage(
            @JsonProperty("roomId") String roomId,
            @JsonProperty("userCode") Long userCode,
            @JsonProperty("message") String message,
            @JsonProperty("createdAt") LocalDateTime createdAt) {
        this.roomId = roomId;
        this.userCode = userCode;
        this.message = message;
        this.createdAt = createdAt;
    }
}
