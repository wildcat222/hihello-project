package spring.hi_hello_spring.employee.command.application.dto.hr;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyEmplReqDTO {

    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;
}
