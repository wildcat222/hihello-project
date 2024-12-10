package spring.hi_hello_spring.onboarding.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.onboarding.query.dto.CompletedStatusDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingResDTO;
import spring.hi_hello_spring.onboarding.query.mapper.OnboardingMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingQueryService {

    private final OnboardingMapper onboardingMapper;

    public List<OnboardingDTO> getOnboardingList(Long employeeSeq) {

        List<OnboardingDTO> onboardingList = onboardingMapper.getOnboardingList(employeeSeq);

        CompletedStatusDTO completedStatusDTO = onboardingMapper.getCountStatus(employeeSeq);
        int completedCount = completedStatusDTO.getCompletedCount();
        int totalCount = completedStatusDTO.getTotalCount();
        double onboardingProgress = 0.0;
        if (totalCount > 0) {
            onboardingProgress = (double) completedCount / totalCount * 100;
        }

        OnboardingResDTO onboardingResDTO = new OnboardingResDTO();
        onboardingResDTO.createResOnboarding(onboardingList, onboardingProgress);

        return onboardingList;
    }
}
