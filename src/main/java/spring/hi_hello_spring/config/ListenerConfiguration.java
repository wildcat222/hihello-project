package spring.hi_hello_spring.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import software.amazon.awssdk.utils.ImmutableMap;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.domain.aggregate.ChatMessage;

import java.util.Map;

@EnableKafka
@Configuration
public class ListenerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    // KafkaListener 컨테이너 팩토리를 생성하는 Bean 메서드
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatRequestMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatRequestMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // Kafka ConsumerFactory를 생성하는 Bean 메서드
    @Bean
    public ConsumerFactory<String, ChatRequestMessage> consumerFactory() {
        JsonDeserializer<ChatRequestMessage> deserializer = new JsonDeserializer<>(ChatRequestMessage.class);
        deserializer.addTrustedPackages("*");  // 모든 패키지 신뢰

        // Kafka Consumer 구성을 위한 설정값들
        Map<String, Object> consumerConfigurations = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)  // Kafka 서버 주소
                .put(ConsumerConfig.GROUP_ID_CONFIG, "chat-group")  // Consumer 그룹 ID
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)  // Key 직렬화 방식
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)  // Value 직렬화 방식 (ChatRequestMessage)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")  // 가장 오래된 메시지부터 시작
                .build();

        return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
    }
}
