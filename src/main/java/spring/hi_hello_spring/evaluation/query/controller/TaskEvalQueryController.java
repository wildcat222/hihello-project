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
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalDetailsQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalListQueryDTO;
import spring.hi_hello_spring.evaluation.query.service.TaskEvalQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-submit")
@RequiredArgsConstructor
@Tag(name = "TaskEval API", description = "과제 평가 관련 API")
public class TaskEvalQueryController {

    private final TaskEvalQueryService taskEvalQueryService;

    @GetMapping
    @Operation(summary = "과제 평가 전체 조회", description = "과제 평가 전체 조회 로직입니다.")
    public ApiResponse<?> getTaskEvalList() {
        List<TaskEvalListQueryDTO> taskEvalListQueryDTOS = taskEvalQueryService.getAllTaskEvals();
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", taskEvalListQueryDTOS).getBody();
    }

    @GetMapping("/{taskSubmitSeq}/task-eval")
    @Operation(summary = "과제 평가 상세 조회", description = "과제 평가 상세 조회 로직입니다.")
    public ApiResponse<?> getTaskEvalDetails(@PathVariable("taskSubmitSeq") Long taskSubmitSeq) {
        List<TaskEvalDetailsQueryDTO> taskEvalDetailsQueryDTOS = taskEvalQueryService.getTaskEvalDetails(taskSubmitSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", taskEvalDetailsQueryDTOS).getBody();
    }
}