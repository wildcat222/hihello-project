package spring.hi_hello_spring.chatting;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 메시지를 구독할 때 사용하는 prefix
        config.enableSimpleBroker("/sub");

        // 클라이언트가 메시지를 발행할 때 사용하는 prefix
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트에서 연결할 WebSocket endpoint
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*") ; // 허용할 특정 도메인으로 변경
//                .withSockJS();  // SockJS 지원
    }
}
