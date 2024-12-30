package spring.hi_hello_spring.elasticsearch.command.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import spring.hi_hello_spring.elasticsearch.command.document.WikiDocument;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public interface WikiDocumentRepository extends ElasticsearchRepository<WikiDocument, String> {

    // 제목으로 검색하는 메서드
    @Query("""
            {
                       "bool": {
                         "should": [
                           {
                             "match": {
                               "wiki_title.prefix": {
                                 "query": "?0",
                                 "boost": 2.0
                               }
                             }
                           },
                           {
                             "match": {
                               "wiki_title": {
                                 "query": "?0",
                                 "boost": 1.0,
                                 "fuzziness": "AUTO",
                                 "prefix_length": 1
                               }
                             }
                           },
                           {
                             "wildcard": {
                               "wiki_title": {
                                 "value": "*?0*",
                                 "boost": 0.5
                               }
                             }
                           }
                         ],
                         "minimum_should_match": 1
                       }
                     }
            """)
    List<WikiDocument> searchByWikiTitle(String keyword);
}