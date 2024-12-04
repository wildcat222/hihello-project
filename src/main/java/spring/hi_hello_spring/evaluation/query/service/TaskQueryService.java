package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.TaskHrAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskQueryService {
    private final TaskQueryMapper taskQueryMapper;

    public List<TaskHrAllListQueryDTO> getHrAllTaskList() {

        return taskQueryMapper.findHrAllTask();
    }
}
