package spring.hi_hello_spring.mentoring.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "mentoring")
@NoArgsConstructor
@Getter
public class Mentoring extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentoringSeq;

    private Long mentorSeq;

    private Long menteeSeq;

    private boolean mentoringActiveStatus = true;

    private String chatRoomSeq;

    @Builder
    public Mentoring(Long mentorSeq, Long menteeSeq, String chatRoomSeq) {
        this.mentorSeq = mentorSeq;
        this.menteeSeq = menteeSeq;
        this.chatRoomSeq = chatRoomSeq;
    }
}
