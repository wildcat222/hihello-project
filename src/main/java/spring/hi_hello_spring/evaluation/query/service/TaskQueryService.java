package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskQueryService {
    private final TaskQueryMapper taskQueryMapper;

    public List<TaskAllListQueryDTO> getHrAllTaskList() {

        return taskQueryMapper.findHrAllTask();
    }

    public List<TaskAllListQueryDTO> getMentorAllTaskList() {

        Long employeeSeq = 3L; // 로그인 기능 완성되면 코드 수정하겠습니다.

        return taskQueryMapper.findMentorAllTask(employeeSeq);
    }
}
