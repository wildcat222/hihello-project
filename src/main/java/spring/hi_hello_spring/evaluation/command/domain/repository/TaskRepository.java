package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskSubmit;

public interface TaskRepository {
    Task save(Task task);

    TaskSubmit save(TaskSubmit taskSubmit);

    boolean existsById(Long taskSeq);

    void deleteById(Long taskSeq);
}


