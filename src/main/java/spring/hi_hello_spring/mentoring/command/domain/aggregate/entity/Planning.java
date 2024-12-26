package spring.hi_hello_spring.mentoring.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "planning")
@NoArgsConstructor
@Getter
public class Planning extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planningSeq;

    private Long employeeSeq;

    private String planningName;

    @Lob // 대량 데이터를 처리
    @Column(columnDefinition = "TEXT")
    private String planningContent;

    @Enumerated(EnumType.STRING)
    private PlanningStatus planningStatus = PlanningStatus.PENDING;

    @Builder
    public Planning(Long employeeSeq, String planningName, String planningContent) {
        this.employeeSeq = employeeSeq;
        this.planningName = planningName;
        this.planningContent = planningContent;
    }
}
