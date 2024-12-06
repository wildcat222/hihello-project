package spring.hi_hello_spring.employee.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.aggregate.entity.EmployeeRole;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.command.application.dto.hr.CreateEmplReqDTO;
import spring.hi_hello_spring.employee.command.application.service.HRService;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
@Tag(name = "Employee API")
public class HRController {

    private final HRService hrService;

    @Operation(summary = "사원 생성", description = "담당자가 사원을 생성한다.")
    @PostMapping("/user")
    public ApiResponse<?> createEmployee(@RequestBody CreateEmplReqDTO createEmplReqDTO) {

        hrService.createEmpl(createEmplReqDTO);

        return ResponseUtil.successResponse("사원이 등록되었습니다.").getBody();
    }

    @Operation(summary = " 사원 정보 수정", description = "담당자가 사원의 정보를 수정한다.")
    @PutMapping("/user/{employeeSeq}")
    public ApiResponse<?> modifyEmployee(@PathVariable Long employeeSeq,
                                         @RequestBody EmployeeRole employeeRole) {

        hrService.modifyEmpl(employeeSeq, employeeRole);
        return ResponseUtil.successResponse("사원 정보가 수정되었습니다.").getBody();
    }
}
