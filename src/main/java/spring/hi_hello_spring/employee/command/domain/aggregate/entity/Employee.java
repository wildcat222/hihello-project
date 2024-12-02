package spring.hi_hello_spring.employee.command.domain.aggregate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

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

    private String employeeRole;

    private String employeePassword;

    private Boolean employeeDeletedStatus;

}


