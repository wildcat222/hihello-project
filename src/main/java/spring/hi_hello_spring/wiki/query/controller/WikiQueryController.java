package spring.hi_hello_spring.wiki.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.wiki.query.dto.WikiListQueryDTO;
import spring.hi_hello_spring.wiki.query.service.WikiQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
public class WikiQueryController {

    private final WikiQueryService wikiQueryService;

    // 위키 리스트 조회
    @GetMapping
    @Operation(summary = "위키 리스트 전체 조회", description = "위키 리스트 전체 조회 로직입니다.")
    public ApiResponse<?> getAllWikis() {
        List<WikiListQueryDTO> wikiListQueryDTOs = wikiQueryService.getAllWikis();
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", wikiListQueryDTOs).getBody();
    }
}