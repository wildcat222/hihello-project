package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;

import java.util.Optional;

public interface OnboardingStatusRepository {
    Optional<OnboardingStatus> findById(Long onboardingStatusSeq);

    OnboardingStatus save(OnboardingStatus onboardingStatus);
}
