package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalListQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskEvalMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEvalQueryService {

    private final TaskEvalMapper taskEvalMapper;

    public List<TaskEvalListQueryDTO> getAllTaskEvals() {
        return taskEvalMapper.findAllTaskEvals();
    }
}