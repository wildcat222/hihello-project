package spring.hi_hello_spring.employee.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.command.application.dto.employee.ModifyPasswordReqDTO;
import spring.hi_hello_spring.employee.command.application.service.EmployeeService;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
@Tag(name = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "로그아웃", description = "사원이 로그아웃을 한다.")
    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestHeader("Authorization") String accessToken) {
        System.out.println(accessToken);
        employeeService.logout(accessToken);
        return ResponseUtil.successResponse("로그아웃을 완료하였습니다.").getBody();
    }

    @Operation(summary = "비밀번호 변경", description = "사원이 비밀번호를 변경한다.")
    @PutMapping("/{employeeSeq}/password")
    public ApiResponse<?> modifyPassword(@PathVariable Long employeeSeq,
                                         @RequestBody ModifyPasswordReqDTO modifyPwdDTO) {

        try {
            employeeService.modifyPwd(employeeSeq, modifyPwdDTO);
        } catch (CustomException e) {
            if (e.getErrorCode().equals(ErrorCodeType.USER_PWD_INCORRECT)) {
                return ResponseUtil.failureResponse("비밀번호가 올바르지 않습니다.", "USER_ERROR_004").getBody();
            } else if (e.getErrorCode().equals(ErrorCodeType.NEW_PWD_MISMATCH)) {
                return ResponseUtil.failureResponse("새 비밀번호 확인 시 일치하지 않습니다.", "USER_ERROR_005").getBody();
            } else if (e.getErrorCode().equals(ErrorCodeType.PWD_SAME_AS_OLD)) {
                return ResponseUtil.failureResponse("기존 비밀번호와 새 비밀번호가 동일합니다.", "USER_ERROR_006").getBody();
            }
        }
        return ResponseUtil.successResponse("비밀번호를 변경하였습니다.").getBody();
    }

}
