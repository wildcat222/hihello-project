package spring.hi_hello_spring.quiz.command.domain.repository;

import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.Quiz;

public interface QuizRepository {
    Quiz save(Quiz quiz);
}
