package spring.hi_hello_spring.employee.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;
import spring.hi_hello_spring.employee.query.dto.MenteeDepQueryDTO;
import spring.hi_hello_spring.employee.query.mapper.EmployeeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeQueryService {

    private final EmployeeMapper employeeMapper;

    public List<MenteeAllQueryDTO> getAllEmployee() {

        return employeeMapper.findAllMentee();
    }

    public List<MenteeDepQueryDTO> getDepMentees(Long departmentSeq) {

        List<MenteeDepQueryDTO>  menteeDepQueryDTO = employeeMapper.findMenteeByDep(departmentSeq);
        if(menteeDepQueryDTO == null) {
            throw new IllegalArgumentException("해당 부서의 멘티가 존재하지 않습니다");
        }
        return menteeDepQueryDTO;
    }
}
