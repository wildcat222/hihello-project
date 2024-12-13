package spring.hi_hello_spring.wiki.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WikiQueryDTO {

    private String wikiTitle;
    private String wikiContent;
    private LocalDateTime modDate;
}