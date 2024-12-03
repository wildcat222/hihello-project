package spring.hi_hello_spring.employee.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Entity
@Table(name="employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Employee extends BaseTimeEntity {

    @Id
    private Long employeeSeq;

    private Long departmentSeq;

    private Long positionSeq;

    private String employeeNum;

    private String employeeName;

    private String employeePhone;

    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole = EmployeeRole.MENTEE;

    private String employeePassword;

    private Boolean employeeDeletedStatus;

}


