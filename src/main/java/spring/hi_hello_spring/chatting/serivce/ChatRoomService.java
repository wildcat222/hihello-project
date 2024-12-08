package spring.hi_hello_spring.chatting.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.chatting.domain.aggregate.ChatMessage;
import spring.hi_hello_spring.chatting.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.dto.ChatRoom;
import spring.hi_hello_spring.chatting.domain.repository.mongo.ChatMessageMongoRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final MentoringRepository mentoringRepository;  // Mentoring 엔티티 사용
    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final ModelMapper modelMapper;

    // Mentoring을 사용해 채팅방 생성
    public void createChatRoom(Long mentoringSeq, Long mentorSeq, Long menteeSeq) {

        Mentoring mentoring = mentoringRepository.findByMentoringSeq(mentoringSeq);

        ChatRoom chatRoom = modelMapper.map(mentoring, ChatRoom.class);

        chatRoom.setRoomId(mentoring.getMentoringSeq());  // mentoringSeq를 roomId로 사용
        chatRoom.setMentorSeq(mentorSeq);
        chatRoom.setMenteeSeq(menteeSeq);

        System.out.println("새로운 방 생성 됨: " + chatRoom.getRoomId());
    }

    public void saveChatMessage(Long roomId, ChatRequestMessage requestMessage) {
        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(roomId)
                .userCode(requestMessage.getUserCode())
                .message(requestMessage.getMessage())
                .createdAt(requestMessage.getCreatedAt())
                .build();

        chatMessageMongoRepository.save(chatMessage);  // MongoDB에 메시지 저장
        System.out.println("mongoDB에 채팅 내용 저장 됨: " + chatMessage.getMessage());
    }
}

