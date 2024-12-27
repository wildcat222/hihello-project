package spring.hi_hello_spring.elasticsearch.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.elasticsearch.command.service.ElasticsearchService;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public class ElasticsearchController {

    private final ElasticsearchService elasticsearchService;

    @PostMapping("/index")
    @Operation(summary = "위키 엘라스틱 서치 인덱스 생성", description = "위키 DB 데이터를 엘라스틱 서치에 동기화하는 로직입니다.")
    public ApiResponse<?> indexWiki() {
        elasticsearchService.indexAllWiki();
        return ResponseUtil.successResponse("위키 엘라스틱 서치 인덱스가 성공적으로 생성되었습니다.").getBody();
    }
}