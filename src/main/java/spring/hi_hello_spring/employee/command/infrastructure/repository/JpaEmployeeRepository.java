package spring.hi_hello_spring.employee.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;

public interface JpaEmployeeRepository extends EmployeeRepository, JpaRepository<Employee, Long> {

}
