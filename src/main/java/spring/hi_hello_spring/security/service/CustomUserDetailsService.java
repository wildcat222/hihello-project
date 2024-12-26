package spring.hi_hello_spring.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.command.application.service.EmployeeService;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Department;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Position;
import spring.hi_hello_spring.employee.command.domain.repository.DepartmentRepository;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.employee.command.domain.repository.PositionRepository;
import spring.hi_hello_spring.security.entity.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmployeeService employeeService;

    @Override
    public CustomUserDetails loadUserByUsername(String employeeNum) throws UsernameNotFoundException {

        /* 인증 토큰에 담긴 employeeNum 이 메소드로 넘어오므로 해당 값을 기준으로 DB 에서 조회한다. */
        Employee loginUser = employeeRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(() -> new UsernameNotFoundException(employeeNum));

        Department loginUserDepartment = departmentRepository.findByDepartmentSeq(loginUser.getDepartmentSeq());
        Position loginUserPosition = positionRepository.findByPositionSeq(loginUser.getPositionSeq());

        // 담당자인지 멘토장인지 멘토인지 멘티인지 확인
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(loginUser.getEmployeeRole()))); // 역할 -> mentee

        return new CustomUserDetails(loginUser.getEmployeeSeq(), loginUserDepartment.getDepartmentName()
                , loginUserPosition.getPositionName(), loginUser.getEmployeeNum()
                , loginUser.getEmployeePassword(), grantedAuthorities);
    }

    public CustomUserDetails loadUserBySeq(Long employeeSeq) {

        // 요청온 토큰에서 employeeSeq 이 메소드로 넘어오므로 해당 값을 기준으로 DB 에서 조회한다. */
        Employee employee = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(employeeSeq)));

        Department employeeDepartment = departmentRepository.findByDepartmentSeq(employee.getDepartmentSeq());
        Position employeePosition = positionRepository.findByPositionSeq(employee.getPositionSeq());

        // 담당자인지 멘토장인지 멘토인지 멘티인지 확인
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(employee.getEmployeeRole()))); // 역할 -> mentee

        return new CustomUserDetails(employee.getEmployeeSeq(), employee.getEmployeeNum()
                ,employeeDepartment.getDepartmentName(), employeePosition.getPositionName()
                , employee.getEmployeePassword(), grantedAuthorities);
    }
}
