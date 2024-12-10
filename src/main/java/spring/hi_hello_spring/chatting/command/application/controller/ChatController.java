package spring.hi_hello_spring.chatting.command.application.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;

@RequiredArgsConstructor
@Controller // https 사용 x -> restAPI 아님
@Tag(name = "ChatRequestMessage", description = "채팅 전송 API")
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations messagingTemplate;

    // 메시지 전송
    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/sub/{roomId}")  // 해당 roomId 구독 중인 사용자들에게 메시지 전송
    public ChatRequestMessage sendMessage(ChatRequestMessage message, @DestinationVariable Long roomId) {
        System.out.println("Received message: " + message);
        // 메시지 저장
        chatRoomService.saveChatMessage(roomId, message);

        // 해당 roomId로 메시지 전송 (STOMP 방식)
        messagingTemplate.convertAndSend("/sub/" + roomId, message);

        return message;
    }
}
