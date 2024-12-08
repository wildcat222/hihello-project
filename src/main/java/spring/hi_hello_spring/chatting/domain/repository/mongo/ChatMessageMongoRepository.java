package spring.hi_hello_spring.chatting.domain.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.hi_hello_spring.chatting.domain.aggregate.ChatMessage;


@Repository
public interface ChatMessageMongoRepository extends MongoRepository<ChatMessage, Long> {
    // MongoDB에 저장할 때 필요한 쿼리 메소드들 추가 가능
}
