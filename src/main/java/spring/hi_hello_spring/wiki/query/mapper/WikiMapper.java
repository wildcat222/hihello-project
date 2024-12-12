package spring.hi_hello_spring.wiki.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import spring.hi_hello_spring.wiki.query.dto.WikiHistoryListQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiListQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiModContentQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiSnapshotQueryDTO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WikiMapper {

    List<WikiListQueryDTO> findAllWikis();
    List<WikiHistoryListQueryDTO> findWikiHistories(Long wikiSeq);
    WikiSnapshotQueryDTO findWikiSnapshotByWikiSeq(Long wikiSeq);
    List<WikiModContentQueryDTO> findWikiModContentsByWikiSeqAndWikiSnapshotSeq(
            @Param("wikiSeq") Long wikiSeq,
            @Param("wikiSnapshotSeq") Long wikiSnapshotSeq
    );
    LocalDateTime findWikiLatestModDateByWikiSeq(Long wikiSeq);
}