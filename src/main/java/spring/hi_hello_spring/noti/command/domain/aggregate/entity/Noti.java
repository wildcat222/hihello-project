package spring.hi_hello_spring.noti.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "noti")
@NoArgsConstructor
@Getter
public class Noti extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notiSeq;

    private Long employeeSeq;

    private Long templateSeq;

    private String notiContent;

    private String notiUrl;

    private Boolean alarmReadStatus = false;
}
