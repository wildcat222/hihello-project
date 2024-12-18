package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalDetailsQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskEvalListQueryDTO;

import java.util.List;

@Mapper
public interface TaskEvalQueryMapper {

    List<TaskEvalListQueryDTO> findAllTaskEvals();

    List<TaskEvalDetailsQueryDTO> findTaskEvalDetails(Long taskSubmitSeq);
}