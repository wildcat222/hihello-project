package spring.hi_hello_spring.wiki.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.wiki.query.dto.WikiHistoryListQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiListQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiQueryDTO;
import spring.hi_hello_spring.wiki.query.elasticsearch.document.WikiDocument;
import spring.hi_hello_spring.wiki.query.service.WikiQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
@ConditionalOnProperty(name = "spring.data.elasticsearch.repositories.enabled", havingValue = "true")
public class WikiQueryController {

    private final WikiQueryService wikiQueryService;

    @GetMapping
    @Operation(summary = "위키 리스트 전체 조회", description = "위키 리스트 전체 조회 로직입니다.")
    public ApiResponse<?> getAllWikis() {
        List<WikiListQueryDTO> wikiListQueryDTOs = wikiQueryService.getAllWikis();
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", wikiListQueryDTOs).getBody();
    }

    @GetMapping("/{wikiSeq}/history")
    @Operation(summary = "위키 히스토리 조회", description = "위키 히스토리 조회 로직입니다.")
    public ApiResponse<?> getWikiHistories(@PathVariable("wikiSeq") Long wikiSeq) {
        List<WikiHistoryListQueryDTO> wikiHistoryListQueryDTOS = wikiQueryService.getWikiHistories(wikiSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", wikiHistoryListQueryDTOS).getBody();
    }

    @GetMapping("/{wikiSeq}")
    @Operation(summary = "위키 조회", description = "위키 조회 로직입니다.")
    public ApiResponse<?> getWiki(@PathVariable("wikiSeq") Long wikiSeq) {
        WikiQueryDTO wikiQueryDTO = wikiQueryService.getWikiByWikiSeq(wikiSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", wikiQueryDTO).getBody();
    }

    @GetMapping("/{wikiSeq}/ver/{wikiModContentSeq}")
    @Operation(summary = "위키의 특정 버전 내용 조회", description = "위키의 특정 버전의 내용을 조회하는 로직입니다.")
    public ApiResponse<?> getWikiByWikiModContentSeq(
            @PathVariable("wikiSeq") Long wikiSeq,
            @PathVariable("wikiModContentSeq") Long wikiModContentSeq
    ) {
        WikiQueryDTO wikiQueryDTO = wikiQueryService.getWikiByWikiSeqAndWikiModContentSeq(wikiSeq, wikiModContentSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", wikiQueryDTO).getBody();
    }

    @GetMapping("/search")
    @Operation(summary = "위키 제목 검색", description = "위키 제목을 통해 검색하는 로직입니다.")
    public ApiResponse<?> searchByWikiTitle(@RequestParam String keyword) {
        List<WikiDocument> wikiDocumentList = wikiQueryService.searchWiki(keyword);
        return ResponseUtil.successResponse("위키 검색 결과가 성공적으로 조회되었습니다.", wikiDocumentList).getBody();
    }

    @PostMapping("/index")
    @Operation(summary = "위키 엘라스틱 서치 인덱스 생성", description = "위키 DB 데이터를 엘라스틱 서치에 동기화하는 로직입니다.")
    public ApiResponse<?> indexWiki() {
        wikiQueryService.indexAllWiki();
        return ResponseUtil.successResponse("위키 엘라스틱 서치 인덱스가 성공적으로 생성되었습니다.").getBody();
    }
}