package spring.hi_hello_spring.onboarding.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.query.mapper.TaskSubmitQueryMapper;
import spring.hi_hello_spring.mentoring.query.dto.MenteeQueryDTO;
import spring.hi_hello_spring.onboarding.query.dto.CompletedStatusDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingResDTO;
import spring.hi_hello_spring.onboarding.query.mapper.OnboardingMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingQueryService {

    private final OnboardingMapper onboardingMapper;
    private final TaskSubmitQueryMapper taskSubmitQueryMapper;

    public OnboardingResDTO getOnboardingListByMentee() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        List<OnboardingDTO> onboardingList = onboardingMapper.getOnboardingListByMentee(employeeSeq);
        CompletedStatusDTO completedStatusDTO = onboardingMapper.getCountStatus(employeeSeq);

        int completedCount = completedStatusDTO.getCompletedCount();
        int totalCount = completedStatusDTO.getTotalCount();
        double onboardingProgress = 0.0;
        if (totalCount > 0) {
            onboardingProgress = (double) completedCount / totalCount * 100;
        }

        OnboardingResDTO onboardingResDTO = new OnboardingResDTO();
        onboardingResDTO.createResOnboarding(onboardingList, onboardingProgress);

        return onboardingResDTO;
    }

    public OnboardingResDTO getOnboardingListByMentor() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        MenteeQueryDTO mentee = taskSubmitQueryMapper.findMenteeEmployeeSeq(employeeSeq);
        Long menteeSeq = mentee.getMenteeSeq();

        List<OnboardingDTO> onboardingList = onboardingMapper.getOnboardingListByMentor(employeeSeq, menteeSeq);
        CompletedStatusDTO completedStatusDTO = onboardingMapper.getCountStatus(employeeSeq);

        int completedCount = completedStatusDTO.getCompletedCount();
        int totalCount = completedStatusDTO.getTotalCount();
        double onboardingProgress = 0.0;
        if (totalCount > 0) {
            onboardingProgress = (double) completedCount / totalCount * 100;
        }

        OnboardingResDTO onboardingResDTO = new OnboardingResDTO();
        onboardingResDTO.createResOnboarding(onboardingList, onboardingProgress);

        return onboardingResDTO;
    }
}
