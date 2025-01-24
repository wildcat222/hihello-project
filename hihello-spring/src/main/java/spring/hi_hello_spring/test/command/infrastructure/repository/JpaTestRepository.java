package spring.hi_hello_spring.test.command.infrastructure.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;
import spring.hi_hello_spring.test.command.domain.repository.TestRepository;

public interface JpaTestRepository extends TestRepository, JpaRepository<Test, Long> {
}
