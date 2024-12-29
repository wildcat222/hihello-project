package spring.hi_hello_spring.group.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.group.query.dto.TaskGroupListQueryDTO;
import spring.hi_hello_spring.group.query.dto.TaskGroupMembersQueryDTO;
import spring.hi_hello_spring.group.query.mapper.TaskGroupMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskGroupQueryService {

    private final TaskGroupMapper taskGroupMapper;

    /* 그룹 과제 별 그룹 리스트 조회 */
    public List<TaskGroupListQueryDTO> getTaskGroupList(Long taskSeq) {
        List<TaskGroupListQueryDTO> taskGroupList = taskGroupMapper.getTaskGroupList(taskSeq);
        int groupIndex = 1;  // 1조부터 시작

        for (TaskGroupListQueryDTO taskGroupListQueryDTO : taskGroupList) {
            taskGroupListQueryDTO.setTaskGroupNum(groupIndex + "조");
            groupIndex++;  // 1조, 2조, 3조...
        }

        return taskGroupList;
    }

    public List<TaskGroupMembersQueryDTO> getTaskGroupMembers(Long taskSubmitSeq) {
        return taskGroupMapper.getTaskGroupMembers(taskSubmitSeq);
    }
}
