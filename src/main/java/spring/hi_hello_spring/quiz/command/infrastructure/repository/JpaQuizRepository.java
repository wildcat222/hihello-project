package spring.hi_hello_spring.quiz.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.Quiz;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizRepository;

public interface JpaQuizRepository extends QuizRepository, JpaRepository<Quiz, Long> {
}
