package spring.hi_hello_spring.mentoring.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.query.dto.MenteeReportListQueryDTO;
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

    @Operation(summary = "(멘토) 멘토링 보고서 리스트 조회", description = "멘토는 멘토링 보고서 리스트를 조회한다.")
    @GetMapping("/mentor/report")
    public ApiResponse<?> getReportListByMentor() {

        List<ReportListQueryDTO> reportList = reportQueryService.getReportListByMentor();
        return ResponseUtil.successResponse("멘토링 보고서 리스트를 조회하였습니다.", reportList).getBody();
    }

    @Operation(summary = "(멘티) 멘토링 보고서 리스트 조회", description = "멘티는 멘토링 보고서 리스트를 조회한다.")
    @GetMapping("/mentee/report")
    public ApiResponse<?> getReportListByMentee() {

        List<MenteeReportListQueryDTO> reportList = reportQueryService.getReportListByMentee();
        return ResponseUtil.successResponse("멘토링 보고서 리스트를 조회하였습니다.", reportList).getBody();
    }

    @Operation(summary = "(담당자, 팀장) 멘토링 보고서 검색", description = "멘토링 보고서를 검색하여 조회한다.")
    @GetMapping("/report/search")
    public ApiResponse<?> getReportSearch(@RequestParam String searchType, @RequestParam String keyword) {

        List<MenteeReportListQueryDTO> reportList = reportQueryService.getReportSearch(searchType, keyword);
        return ResponseUtil.successResponse("성공적으로 멘토링 보고서를 검색하였습니다.", reportList).getBody();
    }

}
