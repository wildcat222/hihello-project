package spring.hi_hello_spring.quiz.command.domain.repository;

import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;

import java.util.Optional;

public interface QuizCategoryRepository {
    QuizCategory save(QuizCategory quizCategory);

    boolean existsById(Long quizCategorySeq);

    void deleteById(Long quizCategorySeq);

    Optional<QuizCategory> findById(Long quizCategorySeq);
}
