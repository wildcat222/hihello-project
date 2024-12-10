package spring.hi_hello_spring.group.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;
import spring.hi_hello_spring.group.command.application.dto.MemberDTO;
import spring.hi_hello_spring.group.command.application.dto.TaskRequestDTO;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;
import spring.hi_hello_spring.group.command.domain.repository.GroupMemberRepository;
import spring.hi_hello_spring.group.command.domain.repository.TaskGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMatchService {

    private final TaskGroupRepository taskGroupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ModelMapper modelMapper;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void createMenteeGroup(List<TaskRequestDTO> taskRequestDTO) {
        int groupNum = 1;
        for (TaskRequestDTO taskRequestDTOs : taskRequestDTO) {
            // TaskGroup 생성 및 저장
            TaskGroup taskSeq = modelMapper.map(taskRequestDTOs, TaskGroup.class);
            taskSeq.updateTaskGroupNum(groupNum++);
            taskSeq = taskGroupRepository.save(taskSeq);

            Long roomId = taskSeq.getTaskGroupSeq(); // 생성된 TaskGroupSeq를 roomID로 사용

            // GroupMember 추가
            for (MemberDTO memberDTO : taskRequestDTOs.getMembers()) {
                GroupMember groupMember = new GroupMember(taskSeq.getTaskGroupSeq(), memberDTO.getEmployeeSeq());
                groupMemberRepository.save(groupMember);
            }

            chatRoomService.createGroupChatRoom(roomId);
        }
    }

}
