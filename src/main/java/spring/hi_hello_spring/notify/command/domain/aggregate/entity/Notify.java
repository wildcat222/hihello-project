package spring.hi_hello_spring.notify.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "noti")
@NoArgsConstructor
@Getter
public class Notify extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiSeq;

    private Long employeeSeq;

    private NotiType notiType;

    private String notiContent;

    private String notiUrl;

    private boolean alarmReadStatus;

    @Builder
    public Notify(Long employeeSeq, NotiType notiType, String notiContent
            , String notiUrl, boolean alarmReadStatus) {
        this.employeeSeq = employeeSeq;
        this.notiType = notiType;
        this.notiContent = notiContent;
        this.notiUrl = notiUrl;
        this.alarmReadStatus = alarmReadStatus;
    }

    // 알림 읽음
    public void notiRead(){
        this.alarmReadStatus = true;
    }
}
