package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.SubmittedTaskQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.MenteeQueryDTO;

@Mapper
public interface TaskSubmitQueryMapper {

    MenteeQueryDTO findMenteeEmployeeSeq(Long employeeSeq);

    SubmittedTaskQueryDTO findSubmittedTask(Long taskSeq, Long menteeSeq);

}