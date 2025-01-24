package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Template extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateSeq;

    private Long quizCategorySeq;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;

    private Boolean templateCheckRequiredStatus;

    private String templateTrainingType;

    private String templateTitle;

    private String templateSub;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String templateDetail;

    private String templateUrlName;

    private Integer templateQuizQty;

    private String templateTaskRound;

    private Integer templateProcedure;

    private LocalDateTime templateEndAt;
}
