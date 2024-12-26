package spring.hi_hello_spring.employee.command.domain.repository;

import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Position;

public interface PositionRepository {

    Position findByPositionName(String position);

    Position findByPositionSeq(Long positionSeq);
}
