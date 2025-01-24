package spring.hi_hello_spring.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import spring.hi_hello_spring.chatting.KafkaConstants;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConstants kafkaConstants;

    public KafkaConsumerConfig(KafkaConstants kafkaConstants) {
        this.kafkaConstants = kafkaConstants;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConstants.KAFKA_BROKER);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConstants.GROUP_ID);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // 만약 받는 DTO가 특정 클래스라면, 이를 지정해줘야 역직렬화 시에 올바르게 처리
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 모든 패키지의 클래스 허용
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ChatResponseMessage.class); // 받을 DTO 클래스 지정

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
