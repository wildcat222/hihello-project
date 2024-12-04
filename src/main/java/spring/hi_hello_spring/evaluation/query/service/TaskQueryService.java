package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.TaskHrAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMenteeDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskQueryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskQueryService {
    private final TaskQueryMapper taskQueryMapper;

    public List<TaskHrAllListQueryDTO> getHrAllTaskList() {

        return taskQueryMapper.findHrAllTask();
    }

    public List<TaskMenteeDetailQueryDTO> getMenteeTaskDetail(Long taskSeq) {

        Map<String, Object> params = new HashMap<>();
        params.put("employee_seq", 5L);
        params.put("task_seq", taskSeq);

        return taskQueryMapper.findMenteeTaskDetail(params);

    }
}
