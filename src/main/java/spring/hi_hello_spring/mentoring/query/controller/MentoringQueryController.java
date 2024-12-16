package spring.hi_hello_spring.mentoring.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanDetailDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanSearchDTO;
import spring.hi_hello_spring.mentoring.query.service.MentoringPlanningQueryService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("api/v1/mentor/planning")
@RequiredArgsConstructor
@Tag(name = "Mentoring Plan API", description = "멘토링 계획서 관련 API")
public class MentoringQueryController {

    private final MentoringPlanningQueryService mentoringPlanningQueryService;

    @GetMapping
    @Operation(summary = "멘토링 계획서 전체 조회", description = "멘토링 계획서 전체 조회 로직입니다.")
    public ApiResponse<?> getMentoringListQuery() {

        List<MentoringPlanListAllQueryDTO> mentoringPlanListAllDTO = mentoringPlanningQueryService.getAllMentoringPlanList();
        return ResponseUtil.successResponse("성공적으로 멘토링 계획서 리스트를 조회하였습니다.", mentoringPlanListAllDTO).getBody();
    }

    @GetMapping("/{planningSeq}")
    @Operation(summary = "멘토링 계획서 상세 조회", description = "멘토링 계획서 상세 조회 로직입니다.")
    public ApiResponse<?> getMentoringPlanDetailQuery(@PathVariable("planningSeq") Long planningSeq) {

        MentoringPlanDetailDTO mentoringPlanDetailDTO = mentoringPlanningQueryService.getMentoringPlanDetail(planningSeq);
        return ResponseUtil.successResponse("성공적으로 멘토링 계획서 상세 내용을 조회하였습니다.", mentoringPlanDetailDTO).getBody();
    }

    @GetMapping("/search")
    @Operation(summary = "멘토링 계획서 검색", description = "멘토링 계획서 검색 로직입니다.")
    public ApiResponse<?> getMentoringPlanSearchQuery(@RequestParam String category, @RequestParam String word) {
        String decodedWord = null;
        try {
            decodedWord = URLDecoder.decode(word, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // System.out.println("멘토링 계획서"+decodedWord);
        List<MentoringPlanSearchDTO> mentoringPlanSearchDTO = mentoringPlanningQueryService.getMentoringPlanSearch(category, decodedWord);
        return ResponseUtil.successResponse("성공적으로 멘토링 계획서 검색을 하였습니다.", mentoringPlanSearchDTO).getBody();
    }
}
