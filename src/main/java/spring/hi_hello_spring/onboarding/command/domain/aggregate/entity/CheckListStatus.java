package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "checklist_status")
@NoArgsConstructor
@Getter
public class CheckListStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkListStatusSeq;

    private Long employeeSeq;

    private Long checkListSeq;

    private Boolean listCheckedStatus = false;
}
