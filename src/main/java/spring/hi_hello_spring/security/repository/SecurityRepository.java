package spring.hi_hello_spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmployeeNum(String employeeSeq);

}
