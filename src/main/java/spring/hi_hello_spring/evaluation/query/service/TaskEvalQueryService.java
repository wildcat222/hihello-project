package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalDetailsQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalListQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskEvalQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEvalQueryService {

    private final TaskEvalQueryMapper taskEvalMapper;

    public List<TaskEvalListQueryDTO> getAllTaskEvals() {
        return taskEvalMapper.findAllTaskEvals();
    }

    public List<TaskEvalDetailsQueryDTO> getTaskEvalDetails(Long taskSubmitSeq) {
        return taskEvalMapper.findTaskEvalDetails(taskSubmitSeq);
    }
}