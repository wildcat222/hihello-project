package spring.hi_hello_spring.finalEval.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalsQueryDTO;
import spring.hi_hello_spring.finalEval.query.service.FinalEvalQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "FinalEval API", description = "최종평가 API")
public class FinalEvalQueryController {

    private final FinalEvalQueryService finalEvalQueryService;

    // 최종 평가 목록 조회
    @GetMapping("/final-eval")
    @Operation(summary = "최종 평가 목록 조회", description = "최종 평가 목록 조회 로직입니다.")
    public ApiResponse<?> finalEvalQuery() {
        List<FinalEvalsQueryDTO> finalEvalListQueryDTOs = finalEvalQueryService.getAllFinalEvals();
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", finalEvalListQueryDTOs).getBody();
    }
}