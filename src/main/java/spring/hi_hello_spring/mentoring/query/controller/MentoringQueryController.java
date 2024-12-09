package spring.hi_hello_spring.mentoring.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;
import spring.hi_hello_spring.mentoring.query.service.MentoringPlanningQueryService;

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
        return ResponseUtil.successResponse("성공적으로 멘토링 계획서를 조회하였습니다.", mentoringPlanListAllDTO).getBody();
    }
}
