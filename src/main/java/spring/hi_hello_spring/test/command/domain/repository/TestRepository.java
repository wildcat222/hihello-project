package spring.hi_hello_spring.test.command.domain.repository;

import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;

public interface TestRepository {
    Test save(Test test);
}
