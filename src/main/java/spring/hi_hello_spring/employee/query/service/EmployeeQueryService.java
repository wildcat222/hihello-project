package spring.hi_hello_spring.employee.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;
import spring.hi_hello_spring.employee.query.mapper.EmployeeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeQueryService {

    private final EmployeeMapper employeeMapper;

    public List<MenteeAllQueryDTO> getAllEmployee() {

        return employeeMapper.findAllMentee();
    }
}
