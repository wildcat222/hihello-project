package spring.hi_hello_spring.employee.command.application.dto.HR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmplReqDTO {

    private String employeeNum;
    private String employeeName;
    private String department;
    private String position;
    private String employeePhone;
    private String employeeEmail;
    private EmployeeRole employeeRole;

}
