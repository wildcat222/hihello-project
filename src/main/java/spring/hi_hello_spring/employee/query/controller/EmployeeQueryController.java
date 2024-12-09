package spring.hi_hello_spring.employee.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;
import spring.hi_hello_spring.employee.query.dto.MenteeDepQueryDTO;
import spring.hi_hello_spring.employee.query.dto.MentorAllQueryDTO;
import spring.hi_hello_spring.employee.query.dto.ReqEmplInfoQueryDTO;
import spring.hi_hello_spring.employee.query.service.EmployeeQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "사원 관련 API")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @GetMapping("/mentee")
    @Operation(summary = "멘티 전체 조회", description = "멘티 전체 조회 로직 입니다.")
    public ApiResponse<?> getAllMentee() {

        List<MenteeAllQueryDTO> menteeAllQueryDTO = employeeQueryService.getAllMentee();
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", menteeAllQueryDTO).getBody();
    }

    @GetMapping("/mentee/{departmentSeq}")
    @Operation(summary = "부서별 멘티 조회", description = "부서별 멘티 조회 로직 입니다.")
    public ApiResponse<?> getEmployeeByDepartmentSeq( @PathVariable Long departmentSeq) {

        List<MenteeDepQueryDTO> menteeDepQueryDTO = employeeQueryService.getDepMentees(departmentSeq);
        return ResponseUtil.successResponse("부서별 멘티 조회가 성공적으로 조회되었습니다.", menteeDepQueryDTO).getBody();
    }

    @GetMapping("/mentor")
    @Operation(summary = "멘토 전체 조회", description = "멘토 전체 조회 로직 입니다.")
    public ApiResponse<?> getAllMentor() {
        List<MentorAllQueryDTO> mentorAllQueryDTO = employeeQueryService.getAllMentor();
        return ResponseUtil.successResponse("멘토 전체 조회가 성공적으로 조회되었습니다.", mentorAllQueryDTO).getBody();
    }

    @Operation(summary = "내 프로필 조회", description = "사원은 본인의 프로필을 조회한다.")
    @GetMapping("/employee/info")
    public ApiResponse<?> getEmployeeInfo() {
        ReqEmplInfoQueryDTO reqEmployeeInfoDTO = employeeQueryService.getEmployeeInfo();
        return ResponseUtil.successResponse("내 정보가 성공적으로 조회되었습니다.", reqEmployeeInfoDTO).getBody();
    }

    @Operation(summary = "멘티/멘토 프로필 조회", description = "멘티/멘토의 프로필을 조회한다.")
    @GetMapping("/mentor/{employeeSeq}/info")
    public ApiResponse<?> getMenteeInfo(@PathVariable Long employeeSeq) {
        ReqEmplInfoQueryDTO reqEmployeeInfoDTO = employeeQueryService.getMentorInfo(employeeSeq);
        return ResponseUtil.successResponse("프로필 정보가 성공적으로 조회되었습니다.", reqEmployeeInfoDTO).getBody();
    }

}
