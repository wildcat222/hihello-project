package spring.hi_hello_spring.onboarding.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.OnboardingStatusRepository;

public interface JpaOnboardingStatusRepository extends OnboardingStatusRepository, JpaRepository<OnboardingStatus, Long> {
}
