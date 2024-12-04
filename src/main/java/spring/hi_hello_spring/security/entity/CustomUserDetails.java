package spring.hi_hello_spring.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private Long employeeSeq;

    public CustomUserDetails(Long employeeSeq, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.employeeSeq = employeeSeq;
    }

    public Long getEmployeeSeq() {
        return employeeSeq;
    }
}
