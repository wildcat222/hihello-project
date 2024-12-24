package spring.hi_hello_spring.chatting.command.application.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;
import spring.hi_hello_spring.chatting.command.domain.aggregate.ChatMessage;
import spring.hi_hello_spring.chatting.command.domain.repository.mongo.ChatMessageMongoRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.employee.query.service.EmployeeQueryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final EmployeeQueryService employeeQueryService;

    // 메시지 저장
    public ChatResponseMessage saveMessage(ChatRequestMessage message) {

        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(message.getRoomId())
                .userSeq(message.getUserCode())
                .message(message.getMessage())
                .createdAt(message.getCreatedAt())
                .build();

        chatMessageMongoRepository.save(chatMessage);  // MongoDB에 메시지 저장

        String sender = employeeQueryService.getEmployeeName(message.getUserCode());
        // user 사진 추가..?

        return ChatResponseMessage.builder()
                .roomId(message.getRoomId())
                .userCode(message.getUserCode())
                .message(chatMessage.getMessage())
                .createdAt(message.getCreatedAt())
                .userName(sender)
                .build();
    }

    // 채팅 내역 조회
    public List<ChatResponseMessage> chattingMessageList(Long userSeq,String roomId) {

        List<ChatMessage> messages = chatMessageMongoRepository.findChatContentByRoomId(roomId);

          // ChatResponseMessage 형태로 변환
        return messages.stream()
                .map(message -> {
                    String sender = employeeQueryService.getEmployeeName(message.getUserSeq());
                    return ChatResponseMessage.builder()
                            .roomId(message.getRoomId())
                            .userCode(message.getUserSeq())
                            .message(message.getMessage())
                            .createdAt(message.getCreatedAt())
                            .userName(sender)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 채팅 삭제
    @Transactional
    public void deleteMessage(String messageId) {
        ChatMessage message = chatMessageMongoRepository.findByMessageId(messageId);

        message.setIsDeleted(true);
        chatMessageMongoRepository.save(message);
    }


}
