package spring.hi_hello_spring.chatting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.hi_hello_spring.chatting.domain.aggregate.ChatMessage;
import spring.hi_hello_spring.chatting.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.dto.ChatRoom;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatRequestMessage, Long> {

    void save(ChatMessage chatMessage);
}