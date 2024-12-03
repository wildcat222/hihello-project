package spring.hi_hello_spring.employee.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;
import spring.hi_hello_spring.employee.query.service.EmployeeQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "사원 관련 API")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @GetMapping("/mentees")
    @Operation(summary = "멘티 전체 조회", description = "멘티 전체 조회 로직 입니다.")
    public ApiResponse<?> getAllEmployee() {

        List<MenteeAllQueryDTO> menteeAllQueryDTO = employeeQueryService.getAllEmployee();
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", menteeAllQueryDTO).getBody();
    }
}
