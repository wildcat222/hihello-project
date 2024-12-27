package spring.hi_hello_spring.elasticsearch.command.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Document(indexName = "wiki")
@Setting(settingPath = "/elasticsearch/settings/settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings/mappings.json")
public class WikiDocument {

    @Id
    @Field(name = "wiki_seq", type = FieldType.Text)
    private String wikiSeq;

    @Field(name = "wiki_title", type = FieldType.Text)
    private String wikiTitle;

    @Field(name = "latest_mod_date", type = FieldType.Date)
    private Date latestModDate;

    public static WikiDocument from(Wiki wiki, LocalDateTime latestModDate) {
        WikiDocument wikiDocument = WikiDocument.builder()
                .wikiSeq(String.valueOf(wiki.getWikiSeq()))
                .wikiTitle(wiki.getWikiTitle())
                .latestModDate(Date.from(latestModDate.atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        wikiDocument.setWikiSeq(String.valueOf(wiki.getWikiSeq()));
        return wikiDocument;
    }

    public WikiDocument(String wikiSeq, String wikiTitle, Date latestModDate) {
        this.wikiSeq = wikiSeq;
        this.wikiTitle = wikiTitle;
        this.latestModDate = latestModDate;
    }
}