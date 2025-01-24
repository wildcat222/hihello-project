package spring.hi_hello_spring.employee.command.domain.repository;

import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Department;

public interface DepartmentRepository {

    Department findByDepartmentName(String department);

    Department findByDepartmentSeq(Long departmentSeq);
}
