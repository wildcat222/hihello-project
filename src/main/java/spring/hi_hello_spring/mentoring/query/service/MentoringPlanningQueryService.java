package spring.hi_hello_spring.mentoring.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;
import spring.hi_hello_spring.mentoring.query.mapper.MentoringPlanMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringPlanningQueryService {
    private final MentoringPlanMapper mentoringPlanMapper;

    public List<MentoringPlanListAllQueryDTO> getAllMentoringPlanList() {
        System.out.println();
        return mentoringPlanMapper.findAllMentoringPlanList();
    }
}
