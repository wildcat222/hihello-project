package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.TaskHrAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMenteeDetailQueryDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskQueryMapper {

    List<TaskHrAllListQueryDTO> findHrAllTask();

    List<TaskMenteeDetailQueryDTO> findMenteeTaskDetail(Map<String, Object> params);
}
