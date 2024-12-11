package spring.hi_hello_spring.chatting.command.domain.aggregate;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "chat_message")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ChatMessage {

    @Id
    private String messageId;
    private String roomId;
    private Long userCode;
    private String message;
    private LocalDateTime createdAt;
}
