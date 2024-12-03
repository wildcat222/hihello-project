package spring.hi_hello_spring.quiz.command.domain.repository;

import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;

public interface QuizCategoryRepository {
    QuizCategory save(QuizCategory quizCategory);
}
