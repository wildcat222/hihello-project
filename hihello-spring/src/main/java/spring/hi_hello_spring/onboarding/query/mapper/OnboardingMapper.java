package spring.hi_hello_spring.onboarding.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.onboarding.query.dto.CompletedStatusDTO;
import spring.hi_hello_spring.onboarding.query.dto.OnboardingDTO;

import java.util.List;

@Mapper
public interface OnboardingMapper {
    List<OnboardingDTO> getOnboardingListByMentee(Long employeeSeq);

    CompletedStatusDTO getCountStatus(Long employeeSeq);

    List<OnboardingDTO> getOnboardingListByMentor(Long employeeSeq, Long menteeSeq);
}
