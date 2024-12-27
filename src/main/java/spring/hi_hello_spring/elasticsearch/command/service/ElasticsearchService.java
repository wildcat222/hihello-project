package spring.hi_hello_spring.elasticsearch.command.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.elasticsearch.command.document.WikiDocument;
import spring.hi_hello_spring.elasticsearch.command.repository.WikiDocumentRepository;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiRepository;
import spring.hi_hello_spring.wiki.query.mapper.WikiMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public class ElasticsearchService {

    private final WikiRepository wikiRepository;
    private final WikiMapper wikiMapper;
    private final WikiDocumentRepository wikiDocumentRepository;

    // DB 데이터를 엘라스틱 서치에 동기화
    @Transactional
    public void indexAllWiki() {
        // DB에서 모든 위키 데이터 조회
        List<Wiki> wikiList = wikiRepository.findAll();

        // WikiDocument 리스트 생성
        List<WikiDocument> wikiDocumentList = new ArrayList<>();
        for (Wiki wiki : wikiList) {
            LocalDateTime latestModDate = wikiMapper.findWikiLatestModDateByWikiSeq(wiki.getWikiSeq());
            WikiDocument wikiDocument = WikiDocument.from(wiki,latestModDate);
            wikiDocumentList.add(wikiDocument);
        }

        // 엘라스틱 서치 저장
        wikiDocumentRepository.saveAll(wikiDocumentList);
    }
}