package spring.hi_hello_spring.chatting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.hi_hello_spring.chatting.dto.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, Long> {

//    ChatRoom findRoomById(Long roomId);
}
