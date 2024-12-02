package spring.hi_hello_spring.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String employeeNum;
    private String employeePassword;
}
