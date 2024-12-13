package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.query.dto.*;
import spring.hi_hello_spring.evaluation.query.mapper.TaskQueryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskQueryService {
    private final TaskQueryMapper taskQueryMapper;

    public List<TaskAllListQueryDTO> getHrAllTaskList() {

        return taskQueryMapper.findHrAllTask();
    }

    public List<TaskMentorDetailQueryDTO> getMentorTaskDetail(Long taskSeq) {
        Long mentorSeq = CustomUserUtils.getCurrentEmployeeSeq();
        Map<String, Object> params = new HashMap<>();
        params.put("employee_seq", mentorSeq);
        params.put("task_seq", taskSeq);

        return taskQueryMapper.findMentorTaskDetail(params);
    }

    public List<TaskAllListQueryDTO> getMentorAllTaskList() {
        Long mentorSeq = CustomUserUtils.getCurrentEmployeeSeq();

        return taskQueryMapper.findMentorAllTask(mentorSeq);
    }


    public List<TaskMenteeDetailQueryDTO> getMenteeTaskDetail(Long taskSeq) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        Map<String, Object> params = new HashMap<>();
        params.put("employee_seq", employeeSeq);
        params.put("task_seq", taskSeq);
        return taskQueryMapper.findMenteeTaskDetail(params);
    }

    /* 그룹 과제 제목 리스트 조회 */
    public List<GroupTaskAllQueryDTO> getGroupTaskTitle() {

        return taskQueryMapper.findGroupTaskTitle();

    }
    /* 과제 검색*/
    public List<TaskSearchQueryDTO> getSearchTask(String taskSearch) {

        return taskQueryMapper.findSearchTask(taskSearch);
    }

    public List<TaskGroupPartnerQueryDTO> getTaskGroupPartner(Long taskGroupSeq) {

        return taskQueryMapper.findTaskGroupPartner(taskGroupSeq);
    }
}

