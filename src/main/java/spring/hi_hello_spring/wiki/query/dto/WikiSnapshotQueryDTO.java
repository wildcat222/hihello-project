package spring.hi_hello_spring.wiki.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WikiSnapshotQueryDTO {

    private Long wikiSeq;
    private String wikiTitle;
    private Long wikiSnapshotSeq;
    private Integer wikiSnapshotVer;
    private String wikiSnapshotContent;
}