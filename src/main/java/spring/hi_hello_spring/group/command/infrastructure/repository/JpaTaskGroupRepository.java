package spring.hi_hello_spring.group.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;
import spring.hi_hello_spring.group.command.domain.repository.TaskGroupRepository;

public interface JpaTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Long> {
}