package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;
import spring.hi_hello_spring.onboarding.command.application.dto.ReqOnboardingStatusDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.OnboardingStatusRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OnboardingStatusService {

    private final OnboardingStatusRepository onboardingStatusRepository;
    private final MentoringRepository mentoringRepository;
    private final NotifyService notifyService;

    public void updateOnboardingStatus(ReqOnboardingStatusDTO reqOnboardingStatusDTO) {

        OnboardingStatus onboardingStatus = onboardingStatusRepository.findById(reqOnboardingStatusDTO.getOnboardingStatusSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        onboardingStatus.updateOnboardingCompletedStatus(true);

        onboardingStatusRepository.save(onboardingStatus);

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);

        notifyService.send(mentoring.getMenteeSeq(), mentoring.getMentorSeq(), NotiType.ONBOARDING_COMPLETE_BY_MENTEE, "");
    }

    public void updateOnboardingStatusByMentor(ReqOnboardingStatusDTO reqOnboardingStatusDTO) {

        OnboardingStatus onboardingStatus = onboardingStatusRepository.findById(reqOnboardingStatusDTO.getOnboardingStatusSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        Mentoring mentoring = mentoringRepository.findByMentorSeqAndMentoringActiveStatusIsTrue(employeeSeq);


        if (Objects.equals(onboardingStatus.getEmployeeSeq(), mentoring.getMenteeSeq())) {
            onboardingStatus.updateOnboardingCompletedStatus(true);
            onboardingStatusRepository.save(onboardingStatus);
            notifyService.send(mentoring.getMentorSeq(), mentoring.getMenteeSeq(), NotiType.ONBOARDING_COMPLETE_BY_MENTOR, "");
        } else {
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }

    }
}


