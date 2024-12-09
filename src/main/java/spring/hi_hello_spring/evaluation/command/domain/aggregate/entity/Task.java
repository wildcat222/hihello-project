package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task")
@NoArgsConstructor
@Getter
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskSeq;

    private Long departmentSeq;

    private Long templateSeq;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    private String taskTitle;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String taskContent;

    public void updateTemplateSeq(Long templateSeq) {
        this.templateSeq = templateSeq;
    }
}