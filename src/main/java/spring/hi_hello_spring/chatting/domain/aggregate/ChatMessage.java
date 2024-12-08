package spring.hi_hello_spring.chatting.domain.aggregate;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_message")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ChatMessage  {

    @Id
    private String messageId;
    private Long roomId;
    private Long userCode;
    private String message;
    private LocalDateTime createdAt;
}
