package spring.hi_hello_spring.employee.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.employee.query.dto.MenteeAllQueryDTO;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<MenteeAllQueryDTO> findAllMentee();
}
