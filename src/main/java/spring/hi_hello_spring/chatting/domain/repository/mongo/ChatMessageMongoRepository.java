package spring.hi_hello_spring.chatting.domain.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.hi_hello_spring.chatting.domain.aggregate.ChatMessage;


@Repository
public interface ChatMessageMongoRepository extends MongoRepository<ChatMessage, Long> {

}
