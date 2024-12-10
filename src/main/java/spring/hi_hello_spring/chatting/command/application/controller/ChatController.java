package spring.hi_hello_spring.chatting.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;

import java.util.List;

@RequiredArgsConstructor
@Controller // https 사용 x -> restAPI 아님
@Tag(name = "ChatRequestMessage", description = "채팅 내역 API")
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

    // 채팅 내역 조회
    @GetMapping("chat/room/{roomId}/message")
    @ResponseBody
    @Operation(summary = "채팅 내역", description = "채팅 내역을 반환합니다.")
    public ApiResponse<String> loadMessage(@PathVariable("roomId") Long roomId) {
        // 채팅 내역을 조회하여 반환
        List<ChatResponseMessage> messages = chatRoomService.chattingMessageList(roomId);
        // return ResponseUtil.successResponse(messages); // 내용 확인용
        return  ResponseUtil.successResponse("채팅 내역이 성공적으로 조회 되었습니다.").getBody();
    }
}
