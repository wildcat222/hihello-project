package spring.hi_hello_spring.employee.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Department;
import spring.hi_hello_spring.employee.command.domain.repository.DepartmentRepository;

public interface JpaDepartmentRepository extends DepartmentRepository, JpaRepository<Department, Long> {
}
