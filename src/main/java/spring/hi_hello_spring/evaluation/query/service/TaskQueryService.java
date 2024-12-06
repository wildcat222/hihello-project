package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.GroupTaskAllQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMenteeDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;
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

    public List<TaskMenteeDetailQueryDTO> getMenteeTaskDetail(Long taskSeq) {

        Map<String, Object> params = new HashMap<>();
        params.put("employee_seq", 5L); // 시큐리티Seq 구현되면 변경 예정
        params.put("task_seq", taskSeq);

        return taskQueryMapper.findMenteeTaskDetail(params);
    }

    public List<TaskAllListQueryDTO> getMentorAllTaskList() {
        Long employeeSeq = 3L; // 로그인 기능 완성되면 코드 수정하겠습니다.
        return taskQueryMapper.findMentorAllTask(employeeSeq);
    }

    /* 그룹 과제 제목 리스트 조회 */
    public List<GroupTaskAllQueryDTO> getGroupTaskTitle() {

        return taskQueryMapper.findGroupTaskTitle();
    }
}


