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
import spring.hi_hello_spring.evaluation.query.dto.SubmittedTaskQueryDTO;
import spring.hi_hello_spring.evaluation.query.service.TaskSubmitQueryService;

@RestController
@RequestMapping("/api/v1/task-submit")
@RequiredArgsConstructor
@Tag(name = "TaskSubmit API", description = "과제 제출 관련 API")
public class TaskSubmitQueryController {

    private final TaskSubmitQueryService taskSubmitQueryService;

    @GetMapping("/{taskSeq}")
    @Operation(summary="(멘토) 제출된 과제 조회", description="멘토가 멘티가 제출한 과제를 상세 조회하는 기능입니다.")
    public ApiResponse<?> getSubmittedTasks(@PathVariable("taskSeq") Long taskSeq) {
        SubmittedTaskQueryDTO submittedTaskQueryDTO = taskSubmitQueryService.getSubmittedTask(taskSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", submittedTaskQueryDTO).getBody();
    }
}