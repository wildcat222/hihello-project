package spring.hi_hello_spring.mentoring.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.query.dto.ReportListQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;
import spring.hi_hello_spring.mentoring.query.service.ReportQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Mentoring Report API", description = "멘토링 보고서 관련 API")
public class ReportQueryController {

    private final ReportQueryService reportQueryService;

    @Operation(summary = "멘토링 보고서 상세 조회", description = "멘토링 보고서를 상세 조회한다.")
    @GetMapping("/report/{reportSeq}")
    public ApiResponse<?> getReportDetail(@PathVariable Long reportSeq) {

        ResMentoringReportDTO reqMentoringReportDTO = reportQueryService.findReportDetail(reportSeq);
        return ResponseUtil.successResponse("멘토링 보고서를 성공적으로 조회하였습니다.", reqMentoringReportDTO).getBody();
    }

    @Operation(summary = "(담당자) 멘토링 보고서 리스트 조회", description = "담당자는 멘토링 보고서 리스트를 조회한다.")
    @GetMapping("/hr/report")
    public ApiResponse<?> getReportListAll() {

        List<ReportListQueryDTO> reportList = reportQueryService.getReportListAll();
        return ResponseUtil.successResponse("멘토링 보고서 리스트를 조회하였습니다.",reportList).getBody();
    }

    @Operation(summary = "(팀장) 멘토링 보고서 리스트 조회", description = "팀장은 멘토링 보고서 리스트를 조회한다.")
    @GetMapping("/leader/report")
    public ApiResponse<?> getReportListByLeader() {

        List<ReportListQueryDTO> reportList = reportQueryService.getReportListByLeader();
        return ResponseUtil.successResponse("멘토링 보고서 리스트를 조회하였습니다.", reportList).getBody();
    }
}
