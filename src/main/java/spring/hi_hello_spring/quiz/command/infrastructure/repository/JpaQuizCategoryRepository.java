package spring.hi_hello_spring.quiz.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizCategoryRepository;

public interface JpaQuizCategoryRepository extends QuizCategoryRepository, JpaRepository<QuizCategory, Long> {
}
