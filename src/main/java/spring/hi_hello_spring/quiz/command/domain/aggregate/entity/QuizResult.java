package spring.hi_hello_spring.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "quiz_result")
@NoArgsConstructor
@Getter
public class QuizResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizResultSeq;

    private Long quizSeq;

    private Long employeeSeq;

    private Boolean quizCorrectStatus;
}
