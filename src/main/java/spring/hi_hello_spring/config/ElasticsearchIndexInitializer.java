package spring.hi_hello_spring.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.index.Settings;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Component
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchIndexInitializer {

    // 엘라스틱서치 작업을 위한 핵심 클래스
    private final ElasticsearchOperations elasticsearchOperations;
    private final ResourceLoader resourceLoader;

    // 위키 검색
    @Value("${spring.elasticsearch.wiki.index.name}")
    private String wiki;

    @PostConstruct
    private void createWikiIndex() {
        try {
            IndexOperations indexOperations = elasticsearchOperations.indexOps(IndexCoordinates.of(wiki));

            // 엘라스틱서치 settings,mappings 적용을 위한 코드
            Resource settingsResource = resourceLoader.getResource("classpath:/elasticsearch/settings/settings.json");
            String settings = StreamUtils.copyToString(settingsResource.getInputStream(), StandardCharsets.UTF_8);

            Resource mappingsResource = resourceLoader.getResource("classpath:/elasticsearch/mappings/mappings.json");
            String mappings = StreamUtils.copyToString(mappingsResource.getInputStream(), StandardCharsets.UTF_8);

            // 해당 인덱스 존재하지 않을 경우 생성
            if (!indexOperations.exists()) {
                log.info("인덱스를 생성합니다.");
                indexOperations.create(Settings.parse(settings));
                indexOperations.putMapping(Document.parse(mappings));
                log.info("인덱스 생성 완료");
            } else {
                log.info("인덱스가 이미 존재합니다.");
            }
        } catch (Exception e) {
            log.error("Wiki Elasticsearch 초기화 실패", e);
            throw new RuntimeException("Elasticsearch 인덱스 업데이트 실패", e);
        }
    }
}