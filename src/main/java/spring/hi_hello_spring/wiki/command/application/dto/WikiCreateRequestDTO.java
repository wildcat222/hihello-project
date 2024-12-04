package spring.hi_hello_spring.wiki.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WikiCreateRequestDTO {

    private String wikiTitle;
    private String wikiSnapshotContent;
}