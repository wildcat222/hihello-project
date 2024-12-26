package spring.hi_hello_spring.mentoring.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanDetailDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanSearchDTO;

import java.util.List;

@Mapper
public interface MentoringPlanMapper {
    List<MentoringPlanListAllQueryDTO> findAllMentoringPlanList();

    MentoringPlanDetailDTO findMentoringPlanDetail(Long planningSeq);

    List<MentoringPlanSearchDTO> findMentoringPlanSearch(String ctg, String word);
}
