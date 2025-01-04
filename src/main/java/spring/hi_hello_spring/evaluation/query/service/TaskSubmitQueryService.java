package spring.hi_hello_spring.evaluation.query.service;

import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.query.dto.SubmittedTaskQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskSubmitQueryMapper;
import spring.hi_hello_spring.mentoring.query.dto.MenteeQueryDTO;

@Service
public class TaskSubmitQueryService {
    private final TaskSubmitQueryMapper taskSubmitQueryMapper;


    public TaskSubmitQueryService(TaskSubmitQueryMapper taskSubmitQueryMapper) {
        this.taskSubmitQueryMapper = taskSubmitQueryMapper;
    }

    public SubmittedTaskQueryDTO getSubmittedTask(Long taskSeq) {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        MenteeQueryDTO mentee = taskSubmitQueryMapper.findMenteeEmployeeSeq(employeeSeq);

        Long menteeSeq = mentee.getMenteeSeq();

        return taskSubmitQueryMapper.findSubmittedTask(taskSeq, menteeSeq);
    }
}