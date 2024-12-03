package spring.hi_hello_spring.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "quiz_category")
@NoArgsConstructor
@Getter
public class QuizCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizCategorySeq;

    private String quizCategoryName;
}
