package spring.hi_hello_spring.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "quiz")
@NoArgsConstructor
@Getter
public class Quiz extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizSeq;

    private Long quizCategorySeq;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String quizQuestion;

    private Boolean quizAnswer;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String quizExplanation;
}
