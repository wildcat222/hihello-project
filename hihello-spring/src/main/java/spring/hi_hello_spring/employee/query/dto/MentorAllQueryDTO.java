package spring.hi_hello_spring.employee.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MentorAllQueryDTO {

    private String employeeNum;
    private String employeeName;
    private String departmentName;
}
