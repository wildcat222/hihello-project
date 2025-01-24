package spring.hi_hello_spring.onboarding.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class OnboardingResDTO {

    private List<OnboardingDTO> onboardingList;
    private double onboardingProgress;

    public void createResOnboarding(List<OnboardingDTO> onboardingList, double onboardingProgress) {
        this.onboardingList = onboardingList;
        this.onboardingProgress = onboardingProgress;
    }
}
