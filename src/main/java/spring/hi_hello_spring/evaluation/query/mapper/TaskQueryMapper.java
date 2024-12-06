package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.GroupTaskAllQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMenteeDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskQueryMapper {

    List<TaskAllListQueryDTO> findHrAllTask();

    List<TaskMenteeDetailQueryDTO> findMenteeTaskDetail(Map<String, Object> params);

    List<TaskAllListQueryDTO> findMentorAllTask(Long employeeSeq);

    List<GroupTaskAllQueryDTO> findGroupTaskTitle();
}
