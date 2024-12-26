package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.SubmittedTaskQueryDTO;

@Mapper
public interface TaskSubmitQueryMapper {

    SubmittedTaskQueryDTO findSubmittedTask(Long taskSubmitSeq);
}