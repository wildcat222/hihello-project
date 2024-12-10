package spring.hi_hello_spring.group.command.domain.repository;

import spring.hi_hello_spring.group.command.domain.aggregate.entity.TaskGroup;

import java.util.Optional;

public interface TaskGroupRepository {

    Optional<TaskGroup> findById(Long taskGroupSeq);

    TaskGroup save(TaskGroup taskSeq);

    TaskGroup findByTaskGroupSeq(Long roomId);
}