package spring.hi_hello_spring.chatting.command.application.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.command.domain.aggregate.ChatMessage;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRequestMessage;
import spring.hi_hello_spring.chatting.command.application.dto.ChatRoom;
import spring.hi_hello_spring.chatting.command.domain.repository.mongo.ChatMessageMongoRepository;
import spring.hi_hello_spring.chatting.command.application.dto.ChatResponseMessage;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;
import spring.hi_hello_spring.group.command.domain.repository.GroupMemberRepository;
import spring.hi_hello_spring.group.command.domain.repository.TaskGroupRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType.CREATE_CHATTING_ROOM;
import static spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType.CREATE_GROUP_CHATTING_ROOM;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final MentoringRepository mentoringRepository;
    private final ChatMessageMongoRepository chatMessageMongoRepository;
    private final ModelMapper modelMapper;
    private final TaskGroupRepository taskGroupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final NotifyService notifyService;
    private final EmployeeRepository employeeRepository;

    // Mentoring을 사용해 채팅방 생성
    @Transactional
    public void createMentoringChatRoom(String roomId, Long mentorSeq, Long menteeSeq) {

        Mentoring mentoring = mentoringRepository.findByChatRoomSeq(roomId);

        ChatRoom chatRoom = modelMapper.map(mentoring, ChatRoom.class);

        chatRoom.setRoomId(roomId);
        chatRoom.setMentorSeq(mentorSeq);
        chatRoom.setMenteeSeq(menteeSeq);

        System.out.println("새로운 맨토링 방 생성 됨: " + chatRoom.getRoomId());

        notifyService.send(mentorSeq, menteeSeq, CREATE_CHATTING_ROOM, "");
    }

    // Grouping을 사용해 채팅방 생성
    @Transactional
    public void createGroupChatRoom(Long senderSeq, String roomId) {
        // Grouping 정보 가져오기
        TaskGroup taskGroup = taskGroupRepository.findByChatRoomSeq(roomId);

        if (taskGroup.getChatRoomSeq() == null) {
            throw new IllegalArgumentException("해당하는 방 없음");
        }

        List<GroupMember> groupMembers = groupMemberRepository.findByTaskGroupSeq(taskGroup.getTaskGroupSeq());

        if (groupMembers.isEmpty()) {
            throw new IllegalArgumentException("TaskGroup에 사람이 없음");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(roomId);

        List<Long> memberSeqList = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            memberSeqList.add(groupMember.getEmployeeSeq());
        }

        chatRoom.setMemberSeqs(memberSeqList);

        notifyService.send(senderSeq, memberSeqList, CREATE_GROUP_CHATTING_ROOM, "");

        System.out.println("새로운 grouping 방 생성됨: " + chatRoom.getRoomId() + ", 인원: " + memberSeqList);
    }


//    public void saveChatMessage(String roomId, ChatRequestMessage requestMessage) {
//        ChatMessage chatMessage = ChatMessage.builder()
//                .roomId(roomId)
//                .userCode(requestMessage.getUserCode())
//                .message(requestMessage.getMessage())
//                .createdAt(requestMessage.getCreatedAt())
//                .build();
//
//        chatMessageMongoRepository.save(chatMessage);  // MongoDB에 메시지 저장
//        System.out.println("mongoDB에 채팅 내용 저장 됨: " + chatMessage.getMessage());
//    }
//
//
//    public List<ChatResponseMessage> chattingMessageList(String roomId) {
//        // MongoDB에서 roomId에 해당하는 메시지 목록을 가져옵니다
//        List<ChatMessage> chatMessages = chatMessageMongoRepository.findByRoomId(roomId);
//
//        // ChatResponseMessage 형태로 변환
//        return chatMessages.stream()
//                .map(message -> new ChatResponseMessage(message.getUserCode(), message.getMessage(), message.getCreatedAt()))
//                .collect(Collectors.toList());
//    }

}

