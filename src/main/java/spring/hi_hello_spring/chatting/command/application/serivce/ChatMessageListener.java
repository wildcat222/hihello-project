package spring.hi_hello_spring.chatting.command.application.serivce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageListener {

    private final ChatRoomService chatRoomService;

    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        log.info("Consumed message: {}", record.value());

        // JSON 파싱 및 저장
        String roomId = record.key();
        String messageJson = record.value();
        ChatRequestMessage message = parseMessage(messageJson);

        chatRoomService.saveChatMessage(roomId, message);
    }

    private ChatRequestMessage parseMessage(String messageJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(messageJson, ChatRequestMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse message JSON", e);
        }
    }
}

