package spring.hi_hello_spring.test.command.domain.repository;

import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;

import java.util.Optional;

public interface TestRepository {
    Test save(Test test);

    Optional<Test> findById(Long testSeq);

    boolean existsById(Long testSeq);

    Test deleteById(Long testSeq);
}
