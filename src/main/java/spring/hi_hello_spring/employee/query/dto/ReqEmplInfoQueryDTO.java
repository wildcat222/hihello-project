package spring.hi_hello_spring.employee.query.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.lang.Nullable;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ReqEmplInfoQueryDTO {

    private String employeeName;
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;
    private String positionName;
    private String employeePhone;
    private String employeeEmail;
    @Nullable
    private String fileUrl;
}
