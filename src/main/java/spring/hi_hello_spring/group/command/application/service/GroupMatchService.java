package spring.hi_hello_spring.group.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.group.command.application.dto.MemberDTO;
import spring.hi_hello_spring.group.command.application.dto.TaskRequestDTO;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;
import spring.hi_hello_spring.group.command.domain.repository.GroupMemberRepository;
import spring.hi_hello_spring.group.command.domain.repository.TaskGroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMatchService {

    private final TaskGroupRepository taskGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createMenteeGroup(List<TaskRequestDTO> taskRequestDTO) {
        for (TaskRequestDTO taskRequestDTOs : taskRequestDTO) {

            TaskGroup taskSeq = new TaskGroup(taskRequestDTOs.getTaskSeq());
            taskSeq = taskGroupRepository.save(taskSeq);

            for (MemberDTO memberDTO : taskRequestDTOs.getMembers()) {
                GroupMember groupMember = new GroupMember(taskSeq.getTaskGroupSeq(), memberDTO.getEmployeeSeq());
                groupMemberRepository.save(groupMember);
            }
        }
    }
}
