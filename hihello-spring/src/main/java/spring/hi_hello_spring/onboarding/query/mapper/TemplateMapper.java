package spring.hi_hello_spring.onboarding.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.onboarding.query.dto.TemplateAllQueryDTO;
import spring.hi_hello_spring.onboarding.query.dto.TemplateTaskRoundDTO;

import java.util.List;

@Mapper
public interface TemplateMapper {
    List<TemplateAllQueryDTO> findAllTemplate();

    List<TemplateTaskRoundDTO> findTemplateTaskRound(String currentEmployeeRole, Long empDepartmentSeq);
}
