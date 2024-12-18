package spring.hi_hello_spring.evaluation.query.service;

import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.SubmittedTaskQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskSubmitQueryMapper;

@Service
public class TaskSubmitQueryService {
    private final TaskSubmitQueryMapper taskSubmitQueryMapper;

    public TaskSubmitQueryService(TaskSubmitQueryMapper taskSubmitQueryMapper) {
        this.taskSubmitQueryMapper = taskSubmitQueryMapper;
    }

    public SubmittedTaskQueryDTO getSubmittedTask(Long taskSubmitSeq) {
        return taskSubmitQueryMapper.findSubmittedTask(taskSubmitSeq);
    }
}