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
import spring.hi_hello_spring.onboarding.query.dto.OnboardingDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingResDTO;
import spring.hi_hello_spring.onboarding.query.service.OnboardingQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Onboarding API", description = "온보딩 관련 API")
public class OnboardingQueryController {

    private final OnboardingQueryService onboardingQueryService;

    @Operation(summary = "멘티 온보딩 리스트 조회", description = "멘티는 온보딩 리스트를 조회한다.")
    @GetMapping("/mentee/onboarding")
    public ApiResponse<?> getOnboardingListByMentee() {

        OnboardingResDTO onboarding = onboardingQueryService.getOnboardingListByMentee();
        return ResponseUtil.successResponse("온보딩 리스트를 조회하였습니다.", onboarding).getBody();
    }

    @Operation(summary = "멘토 온보딩 리스트 조회", description = "멘토는 담당 멘티의 온보딩 리스트를 조회한다.")
    @GetMapping("/mentor/onboarding")
    public ApiResponse<?> getOnboardingListByMentor() {

        OnboardingResDTO onboarding = onboardingQueryService.getOnboardingListByMentor();
        return ResponseUtil.successResponse("온보딩 리스트를 조회하였습니다.", onboarding).getBody();
    }
}
