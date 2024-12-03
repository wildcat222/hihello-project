package spring.hi_hello_spring.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.query.dto.EvalIndAllQueryDTO;
import spring.hi_hello_spring.evaluation.query.service.EvalIndQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/eval/ind")
@RequiredArgsConstructor
@Tag(name = "EvalInd API", description = "평가 지표 관련 API")
public class EvalIndQueryController {

    private final EvalIndQueryService evalIndQueryService;

    /* 평가 지표 조회 */
    @GetMapping
    @Operation(summary = "평가 지표 조회", description = "평가 지표 조회 로직입니다.")
    public ApiResponse<?> getAllEvalInd() {

        List<EvalIndAllQueryDTO> allQueryDTO = evalIndQueryService.findAllEvalInd();
        return ResponseUtil.successResponse("평가 지표가 성공적으로 조회되었습니다.", allQueryDTO).getBody();
    }
}
