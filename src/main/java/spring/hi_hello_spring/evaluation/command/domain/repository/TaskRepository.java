package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;

public interface TaskRepository {
    Task save(Task task);

    boolean existsById(Long taskSeq);

    void deleteById(Long taskSeq);
}


