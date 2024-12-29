package spring.hi_hello_spring.chatting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConstants {

    // @Value를 사용하여 application.yml에서 값을 읽어옵니다.
    @Value("${spring.kafka.consumer.group-id}")
    public String GROUP_ID;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    public String KAFKA_BROKER;

    @Value("${spring.kafka.producer.topic:chat-messages}") // 기본값으로 "chat-messages" 사용
    public String KAFKA_TOPIC;
}