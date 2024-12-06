package spring.hi_hello_spring.employee.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Position;
import spring.hi_hello_spring.employee.command.domain.repository.PositionRepository;

public interface JpaPositionRepository extends PositionRepository, JpaRepository<Position, Long> {
}
