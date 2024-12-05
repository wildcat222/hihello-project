package spring.hi_hello_spring.evaluation.command.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;

public interface JpaTaskRepository extends TaskRepository, JpaRepository<Task, Long> {
}
