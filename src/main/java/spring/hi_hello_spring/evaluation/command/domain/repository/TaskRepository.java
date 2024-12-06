package spring.hi_hello_spring.evaluation.command.domain.repository;

public interface TaskRepository {

    boolean existsById(Long taskSeq);
}