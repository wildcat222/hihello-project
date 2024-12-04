package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.TaskHrAllListQueryDTO;

import java.util.List;

@Mapper
public interface TaskQueryMapper {

    List<TaskHrAllListQueryDTO> findHrAllTask();

}
