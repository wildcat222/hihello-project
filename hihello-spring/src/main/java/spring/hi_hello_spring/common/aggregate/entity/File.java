package spring.hi_hello_spring.common.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file")
@NoArgsConstructor
@Getter
public class File extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    private Long planningSeq;

    private Long taskSeq;

    private Long taskSubmitSeq;

    private Long templateSeq;

    private Long wikiModContentSeq;

    private String fileName;

    @Lob // URL이 긴 경우 @Lob으로 지정하여 TEXT로 매핑
    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    @Builder
    public File(Long taskSeq,Long templateSeq, Long taskSubmitSeq, Long planningSeq, String fileName, String fileUrl) {
        this.taskSeq = taskSeq;
        this.templateSeq = templateSeq;
        this.taskSubmitSeq = taskSubmitSeq;
        this.planningSeq = planningSeq;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }



}
