package spring.hi_hello_spring.chatting.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseMessage {
    private String roomId;
    private Long userCode; // 보낸 사람
    private String message; // 메시지 내용
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 원하는 포맷을 설정합니다.
    private LocalDateTime createdAt;
// user profile
}
