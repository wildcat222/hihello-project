package spring.hi_hello_spring.employee.command.application.dto.employee;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ModifyPasswordReqDTO {

    private String employeePassword;        // 현재 비밀번호
    private String employeeNewPwd;          // 새 비밀번호
    private String checkEmployeeNewPwd;     // 새 비밀번호 확인용
}
