package spring.hi_hello_spring.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private Long employeeSeq;
    private String employeeDepartmentName;
    private String employeePositionName;
    // 부서, 직급 추가


    public CustomUserDetails(Long employeeSeq, String employeeDepartmentName, String employeePositionName, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.employeeSeq = employeeSeq;
        this.employeeDepartmentName = employeeDepartmentName;
        this.employeePositionName = employeePositionName;
    }

}
