package spring.hi_hello_spring.employee.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;
import spring.hi_hello_spring.employee.query.dto.MenteeDepQueryDTO;
import spring.hi_hello_spring.employee.query.dto.MentorAllQueryDTO;
import spring.hi_hello_spring.employee.query.dto.ReqEmplInfoQueryDTO;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<MenteeAllQueryDTO> findAllMentee();

    List<MenteeDepQueryDTO> findMenteeByDep(Long departmentSeq);

    List<MentorAllQueryDTO> findAllMentor();

    ReqEmplInfoQueryDTO findEmployeeInfo(Long employeeSeq);
}
