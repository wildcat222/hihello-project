package spring.hi_hello_spring.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.security.repository.SecurityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityRepository securityRepository;


    @Override
    public UserDetails loadUserByUsername(String employeeNum) throws UsernameNotFoundException {

        /* 인증 토큰에 담긴 employeeNum 이 메소드로 넘어오므로 해당 값을 기준으로 DB 에서 조회한다. */
        Employee loginUser = securityRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(() -> new UsernameNotFoundException(employeeNum));

        // 담당자인지 멘토장인지 멘토인지 멘티인지 확인
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(loginUser.getEmployeeRole()))); // 역할 -> mentee
        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(loginUser.getDepartmentSeq()))); // 직급Seq

        return new CustomUserDetails(loginUser.getEmployeeSeq(), loginUser.getEmployeeNum(), loginUser.getEmployeePassword(), grantedAuthorities);
    }
}
