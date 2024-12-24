package spring.hi_hello_spring.chatting.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;


@Document(collection = "chat_message")
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatMessage {

    @Id
    private String messageId;
    private String roomId;
    private Long userSeq;
    private String message;
    private boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public void setIsDeleted(boolean b) {
        this.isDeleted = b;
    }
}
