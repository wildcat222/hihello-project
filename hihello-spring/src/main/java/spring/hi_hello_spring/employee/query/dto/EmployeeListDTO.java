package spring.hi_hello_spring.employee.query.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class EmployeeListDTO {

    private Long employeeSeq;
    private String employeeNum;
    private String employeeName;
    private String departmentName;
    private String positionName;
    private String employeePhone;
    private String employeeEmail;
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;
}
