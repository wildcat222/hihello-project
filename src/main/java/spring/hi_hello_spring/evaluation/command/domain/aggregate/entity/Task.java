package spring.hi_hello_spring.evaluation.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;

@Entity
@Table(name = "task")
@NoArgsConstructor
@Getter
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskSeq;

    private Long departmentSeq;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    private String taskTitle;

    @Lob // 대량 데이터를 처리하도록 지시
    @Column(columnDefinition = "TEXT")
    private String taskContent;

    public Task(TaskCreateDTO taskCreateDTO) {
        this.departmentSeq = taskCreateDTO.getDepartmentSeq();
        this.taskType = taskCreateDTO.getTaskType();
        this.taskTitle = taskCreateDTO.getTaskTitle();
        this.taskContent = taskCreateDTO.getTaskContent();
    }
}
