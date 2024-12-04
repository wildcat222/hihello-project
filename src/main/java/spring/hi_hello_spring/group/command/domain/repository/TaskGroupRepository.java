package spring.hi_hello_spring.group.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
}
