package spring.hi_hello_spring.finalEval.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.finalEval.command.application.service.FinalEvalService;

@RestController
@RequestMapping("/api/v1/final-eval")
@RequiredArgsConstructor
@Tag(name = "FinalEval API", description = "최종평가 API")
public class FinalEvalController {

    private final FinalEvalService finalEvalService;

    @PostMapping
    @Operation(summary = "최종 평가 등록", description = "최종 평가 등록 로직입니다.")
    public ApiResponse<?> createFinalEval() {
        finalEvalService.createFinalEval();
        return ResponseUtil.successResponse("최종평가가 성공적으로 등록되었습니다.").getBody();
    }
}