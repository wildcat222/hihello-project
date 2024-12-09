package spring.hi_hello_spring.mentoring.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.mentoring.command.domain.repository.PlanningRepository;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanDetailDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanSearchDTO;
import spring.hi_hello_spring.mentoring.query.mapper.MentoringPlanMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringPlanningQueryService {
    private final MentoringPlanMapper mentoringPlanMapper;
    private final PlanningRepository planningRepository;

    public List<MentoringPlanListAllQueryDTO> getAllMentoringPlanList() {

        return mentoringPlanMapper.findAllMentoringPlanList();
    }

    public MentoringPlanDetailDTO getMentoringPlanDetail(Long planningSeq) {

        if (!planningRepository.existsById(planningSeq)) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
        return mentoringPlanMapper.findMentoringPlanDetail(planningSeq);
    }

    public List<MentoringPlanSearchDTO> getMentoringPlanSearch(String category, String word) {

        List<MentoringPlanSearchDTO> results = mentoringPlanMapper.findMentoringPlanSearch(category, word);
        if (results.isEmpty()) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
        return results;
    }
}
