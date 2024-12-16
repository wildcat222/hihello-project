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
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
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
        // JsonDeserializer를 ErrorHandlingDeserializer로 감싸기
        JsonDeserializer<ChatRequestMessage> jsonDeserializer = new JsonDeserializer<>(ChatRequestMessage.class);
        jsonDeserializer.addTrustedPackages("*");


        return new DefaultKafkaConsumerFactory<>(
                ImmutableMap.<String, Object>builder()
                        .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                        .put(ConsumerConfig.GROUP_ID_CONFIG, "chat-group")
                        .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class)
                        .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class)
                        .put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class)
                        .put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class)
                        .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                        .build(),
                new StringDeserializer(), jsonDeserializer
        );
    }
}
