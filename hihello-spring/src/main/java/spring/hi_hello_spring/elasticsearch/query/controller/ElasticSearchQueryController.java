package spring.hi_hello_spring.elasticsearch.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.elasticsearch.command.document.WikiDocument;
import spring.hi_hello_spring.elasticsearch.query.service.ElasticsearchQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public class ElasticSearchQueryController {

    private final ElasticsearchQueryService elasticsearchQueryService;

    @GetMapping("/search")
    @Operation(summary = "위키 제목 검색", description = "위키 제목을 통해 검색하는 로직입니다.")
    public ApiResponse<?> searchByWikiTitle(@RequestParam String keyword) {
        List<WikiDocument> wikiDocumentList = elasticsearchQueryService.searchWiki(keyword);
        return ResponseUtil.successResponse("위키 검색 결과가 성공적으로 조회되었습니다.", wikiDocumentList).getBody();
    }
}