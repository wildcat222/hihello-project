package spring.hi_hello_spring.mentoring.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteReportDTO;
import spring.hi_hello_spring.mentoring.command.application.service.ReportService;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Report API")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "(멘티)보고서 작성", description = "멘티가 보고서를 작성한다.")
    @PostMapping("/{employeeSeq}/report")
    public ApiResponse<?> writeReport(@PathVariable Long employeeSeq,
                                      @RequestBody WriteReportDTO writeReportDTO) {

        reportService.createReport(employeeSeq, writeReportDTO);
        return ResponseUtil.successResponse("보고서가 작성되었습니다.").getBody();
    }

}
