package spring.hi_hello_spring.onboarding.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.query.service.ChecklistQueryService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name="Checklist API", description = "체크리스트 관련 API")
public class ChecklistQueryController {

    private final ChecklistQueryService checklistQueryService;

    @GetMapping("/template/{templateSeq}/checklist/status")
    @Operation(summary = "체크리스트 수행 완료 상태 조회", description = "체크리스트 수행 완료 상태 조회 로직입니다.")
    public ApiResponse<?> getChecklistStatus(@PathVariable("templateSeq") Long templateSeq) {
        boolean checklistStatus = checklistQueryService.getChecklistStatusByTemplateSeq(templateSeq);
        return ResponseUtil.successResponse("체크리스트 수행 완료 상태가 성공적으로 조회되었습니다.", checklistStatus).getBody();
    }
}