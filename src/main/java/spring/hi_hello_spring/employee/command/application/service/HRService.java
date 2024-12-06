package spring.hi_hello_spring.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.command.application.dto.HR.CreateEmplReqDTO;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Department;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Position;
import spring.hi_hello_spring.employee.command.domain.repository.DepartmentRepository;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.employee.command.domain.repository.PositionRepository;

@Service
@RequiredArgsConstructor
public class HRService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createEmpl(CreateEmplReqDTO createEmplReqDTO) {

        Department department = departmentRepository.findByDepartmentName(createEmplReqDTO.getDepartment());
        Position position = positionRepository.findByPositionName(createEmplReqDTO.getPosition());

        String employeePassword = passwordEncoder.encode(createEmplReqDTO.getEmployeeNum());

        Employee newEmployee = modelMapper.map(createEmplReqDTO, Employee.class);
        newEmployee.matchesReq(department.getDepartmentSeq(), position.getPositionSeq(), employeePassword);

        employeeRepository.save(newEmployee);
    }
}
