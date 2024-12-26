package spring.hi_hello_spring.quiz.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QuizCategoryCreateDTO {

    private String quizCategoryName;
}
