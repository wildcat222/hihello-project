package spring.hi_hello_spring.employee.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class DepartmentListDTO {
    private Long departmentSeq;
    private String departmentName;
}
