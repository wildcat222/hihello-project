package spring.hi_hello_spring.wiki.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.hi_hello_spring.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "wiki")
@NoArgsConstructor
@Getter
public class Wiki extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wikiSeq;

    private String wikiTitle;

    private int wikiCurrentVar;

    private boolean wikiDeletedStatus = false;
}
