package spring.hi_hello_spring.elasticsearch.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.elasticsearch.command.document.WikiDocument;
import spring.hi_hello_spring.elasticsearch.command.repository.WikiDocumentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public class ElasticsearchQueryService {

    private final WikiDocumentRepository wikiDocumentRepository;

    /* 엘라스틱 서치 관련 로직 */
    // 위키 검색 로직
    public List<WikiDocument> searchWiki(String keyword) {
        return wikiDocumentRepository.searchByWikiTitle(keyword);
    }
}