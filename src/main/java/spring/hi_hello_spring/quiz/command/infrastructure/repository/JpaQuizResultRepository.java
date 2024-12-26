package spring.hi_hello_spring.quiz.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizResult;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizResultRepository;

public interface JpaQuizResultRepository extends QuizResultRepository, JpaRepository<QuizResult, Long> {
}