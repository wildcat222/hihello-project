package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_submit")
@NoArgsConstructor
@Getter
public class TaskSubmit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskSubmitSeq;

    private Long taskSeq;

    private Long employeeSeq;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String taskSubmitContent;

    private String taskAttachedUrl;

    @Builder
    public TaskSubmit(Long taskSeq, Long employeeSeq, String taskSubmitContent, String taskAttachedUrl){
        this.taskSeq = taskSeq;
        this.employeeSeq = employeeSeq;
        this.taskSubmitContent = taskSubmitContent;
        this.taskAttachedUrl = taskAttachedUrl;
    }
}
