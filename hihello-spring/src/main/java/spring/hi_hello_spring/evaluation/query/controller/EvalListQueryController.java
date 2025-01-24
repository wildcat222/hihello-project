package spring.hi_hello_spring.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.query.dto.EvalListsQueryDTO;
import spring.hi_hello_spring.evaluation.query.service.EvalListQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "EvalList API", description = "과제 평가 항목 관련 API")
public class EvalListQueryController {

    private final EvalListQueryService evalListQueryService;

    @GetMapping("/task/{taskSeq}/eval-list")
    @Operation(summary = "과제 평가 항목 조회", description = "과제 평가 항목 조회 로직입니다.")
    public ApiResponse<?> getTaskEvalLists(@PathVariable("taskSeq") Long taskSeq) {
        List<EvalListsQueryDTO> evalListsQueryDTOS = evalListQueryService.getEvalListsByTaskSeq(taskSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", evalListsQueryDTOS).getBody();
    }
}