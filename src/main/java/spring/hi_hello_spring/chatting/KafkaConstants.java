package spring.hi_hello_spring.chatting;

import java.util.UUID;

public class KafkaConstants {
    //서버 실행 시 환경 변수로 서버마다 다른 그룹 아이디를 가지도록 설정
    public static final String GROUP_ID = "chat-group";
    public static final String KAFKA_TOPIC = "chat-messages";
    public static final String KAFKA_BROKER = "localhost:10000";
}