package spring.hi_hello_spring.wiki.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WikiModContentQueryDTO {

    private Long wikiModContentSeq;
    private Long wikiSeq;
    private Long wikiSnapshotSeq;
    private String wikiModContent;
}