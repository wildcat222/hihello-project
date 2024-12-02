package spring.hi_hello_spring.group.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "task_group")
@NoArgsConstructor
@Getter
public class TaskGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskGroupSeq;

    private Long taskSeq;

    private Boolean taskGroupActiveStatus = true;
}
