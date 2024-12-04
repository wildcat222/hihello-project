package spring.hi_hello_spring.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalIndCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.service.EvalIndService;

@RestController
@RequestMapping("api/v1/hr/eval/ind")
@RequiredArgsConstructor
@Tag(name = "EvalInd API", description = "평가 지표 관련 API")
public class EvalIndController {

    private final EvalIndService evalIndService;

    /* 평가 지표 등록 */
    @PostMapping
    @Operation(summary = "평가 지표 생성", description = "평가 지표 생성 로직입니다.")
    public ApiResponse<?> createEvalInd(EvalIndCreateDTO createDTO){

        evalIndService.createEvalInd(createDTO);
        return ResponseUtil.successResponse("평가 지표가 성공적으로 등록되었습니다.").getBody();
    }

    /* 평가 지표 삭제 */
    @DeleteMapping("/{evalIndSeq}")
    @Operation(summary = "평가 지표 삭제", description = "평가 지표 삭제 로직입니다.")
    public ApiResponse<?> deleteEvalInd(@PathVariable Long evalIndSeq){

        evalIndService.deleteEvalInd(evalIndSeq);
        return ResponseUtil.successResponse("평가 지표가 성공적으로 삭제되었습니다.").getBody();
    }

}
