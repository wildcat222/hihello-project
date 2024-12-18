package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalDetailsQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalListQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.TaskEvalMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEvalQueryService {

    private final EmployeeRepository employeeRepository;
    private final TaskEvalMapper taskEvalMapper;

    public List<TaskEvalListQueryDTO> getAllTaskEvals() {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        Employee employee = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        if(employee.getEmployeeRole().equals(EmployeeRole.MENTOR)) {
            return taskEvalMapper.findTaskEvalsByMentorSeq(employeeSeq);
        }
        return taskEvalMapper.findAllTaskEvals();
    }

    public List<TaskEvalDetailsQueryDTO> getTaskEvalDetails(Long taskSubmitSeq) {
        return taskEvalMapper.findTaskEvalDetails(taskSubmitSeq);
    }
}