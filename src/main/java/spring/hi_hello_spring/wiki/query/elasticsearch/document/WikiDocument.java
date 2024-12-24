package spring.hi_hello_spring.wiki.query.elasticsearch.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;

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

    public static WikiDocument from(Wiki wiki) {
        WikiDocument wikiDocument = WikiDocument.builder()
                .wikiSeq(String.valueOf(wiki.getWikiSeq()))
                .wikiTitle(wiki.getWikiTitle())
                .build();
        wikiDocument.setWikiSeq(String.valueOf(wiki.getWikiSeq()));
        return wikiDocument;
    }

    public WikiDocument(String wikiSeq, String wikiTitle) {
        this.wikiSeq = wikiSeq;
        this.wikiTitle = wikiTitle;
    }
}