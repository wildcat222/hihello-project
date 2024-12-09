package spring.hi_hello_spring.mentoring.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.mentoring.query.dto.MentoringPlanListAllQueryDTO;

import java.util.List;

@Mapper
public interface MentoringPlanMapper {
    List<MentoringPlanListAllQueryDTO> findAllMentoringPlanList();
}
