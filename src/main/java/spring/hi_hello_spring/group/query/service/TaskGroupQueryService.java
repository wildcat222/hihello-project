package spring.hi_hello_spring.group.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.group.query.dto.TaskGroupListQueryDTO;
import spring.hi_hello_spring.group.query.mapper.TaskGroupMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskGroupQueryService {

    private final TaskGroupMapper taskGroupMapper;

    /* 그룹 과제 별 그룹 리스트 조회 */
    public List<TaskGroupListQueryDTO> getTaskGroupList(Long taskSeq) {

        List<TaskGroupListQueryDTO> taskGroupList = taskGroupMapper.getTaskGroupList(taskSeq);

        // 그룹 리스트의 첫 번째 항목부터 "1조", "2조" 식으로 변경
        for (int i = 0; i < taskGroupList.size(); i++) {

            taskGroupList.get(i).setTaskGroupNum((i + 1) + "조");
        }

        return taskGroupList;
    }
}
