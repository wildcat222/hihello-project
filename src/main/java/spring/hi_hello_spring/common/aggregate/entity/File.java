package spring.hi_hello_spring.common.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file")
@NoArgsConstructor
@Getter
public class File extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileSeq;

    private Long planningSeq;

    private Long taskSubmitSeq;

    private Long templateSeq;

    private Long wikiVersionSeq;

    private String fileName;

    private String fileUrl;
}
