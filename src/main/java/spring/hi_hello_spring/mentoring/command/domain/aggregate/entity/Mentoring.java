package spring.hi_hello_spring.mentoring.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "mentoring")
@NoArgsConstructor
@Getter
public class Mentoring extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mentoringSeq;

    private Long mentorSeq;

    private Long menteeSeq;
}
