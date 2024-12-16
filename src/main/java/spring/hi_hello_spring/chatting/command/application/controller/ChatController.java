package spring.hi_hello_spring.chatting.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatMessageListener;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
//@RequestMapping("api/v1/chat")
@Controller // https 사용 x -> restAPI 아님
@Tag(name = "ChatRequestMessage", description = "채팅 내역 API")
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final KafkaTemplate<String, ChatRequestMessage> kafkaTemplate;
    private final ObjectMapper objectMapper;  // ObjectMapper를 주입받기
    private final ChatMessageListener chatMessageListener;

    // 메시지 전송
    @PostMapping("/{roomId}/sendMessage")
    public ResponseEntity<Void> sendMessage(@PathVariable String roomId, @RequestBody ChatRequestMessage message) {
        log.info("Received message: {}", message);

        try {
            objectMapper.writeValueAsString(message);
            chatRoomService.saveChatMessage(roomId, message);
            // Kafka에 메시지 발행 (JSON 형태로)
            kafkaTemplate.send("chat-messages", roomId, message);
//            chatMessageListener.sendMessage(message);

        } catch (Exception e) {
            log.error("Failed to serialize message", e);
            return ResponseEntity.status(500).build();  // 직렬화 실패 시 500 오류 반환
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/{roomId}/message")
    @ResponseBody
    @Operation(summary = "채팅 내역", description = "채팅 내역을 반환합니다.")
    public ResponseEntity<ApiResponse<List<ChatResponseMessage>>> loadMessage(@PathVariable("roomId") String roomId) {
        // 채팅 내역을 조회하여 반환
        List<ChatResponseMessage> messages = chatRoomService.chattingMessageList(roomId);
        return ResponseUtil.successResponse(messages); // 내용 확인용
    }
}
