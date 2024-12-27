package spring.hi_hello_spring.onboarding.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.command.application.dto.ReqOnboardingStatusDTO;
import spring.hi_hello_spring.onboarding.command.application.service.OnboardingStatusService;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@Tag(name = "Onboarding API", description = "온보딩 관련 API")
public class OnboardingStatusController {

    private final OnboardingStatusService onboardingStatusService;

    @Operation(summary = "(멘티) 온보딩 수행 상태 변경", description = "온보딩 수행 상태 변경 로직입니다.")
    @PutMapping("/mentee/onboarding/status/update")
    public ApiResponse<?> updateOnboardingStatus(@RequestBody ReqOnboardingStatusDTO reqOnboardingStatusDTO) {

        onboardingStatusService.updateOnboardingStatus(reqOnboardingStatusDTO);
        return ResponseUtil.successResponse("온보딩 수행 상태가 성공적으로 수정되었습니다.").getBody();
    }

    @Operation(summary = "(멘토) 온보딩 수행 상태 변경", description = "온보딩 수행 상태 변경 로직입니다.")
    @PutMapping("/mentor/onboarding/status/update")
    public ApiResponse<?> updateOnboardingStatusByMentor(@RequestBody ReqOnboardingStatusDTO reqOnboardingStatusDTO) {

        onboardingStatusService.updateOnboardingStatusByMentor(reqOnboardingStatusDTO);
        return ResponseUtil.successResponse("온보딩 수행 상태가 성공적으로 수정되었습니다.").getBody();
    }
}
