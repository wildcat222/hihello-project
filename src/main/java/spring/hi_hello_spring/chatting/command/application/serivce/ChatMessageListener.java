package spring.hi_hello_spring.chatting.command.application.serivce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String TOPIC = "chat-messages"; // Kafka 토픽 이름

    @Autowired
    private KafkaTemplate<String, ChatRequestMessage> kafkaTemplate;

    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void consumeMessage(ConsumerRecord<String, ChatRequestMessage> record) {

        log.info("Consumed message: Key (roomId)={}, Value (message)={}", record.key(), record.value().getMessage());

        messagingTemplate.convertAndSend("/sub/", record.key());  // 구독 경로
        log.info("Consumed message: Key (roomId)={}", record.key());
    }

    public void sendMessage(ChatRequestMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}

