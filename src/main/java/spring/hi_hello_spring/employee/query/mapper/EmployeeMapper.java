package spring.hi_hello_spring.employee.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.hi_hello_spring.employee.query.dto.*;
import spring.hi_hello_spring.employee.query.dto.DepartmentListDTO;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<MenteeAllQueryDTO> findAllMentee();

    List<MenteeDepQueryDTO> findMenteeByDep(Long departmentSeq);

    List<MentorAllQueryDTO> findAllMentor();

    ReqEmplInfoQueryDTO findEmployeeInfo(Long employeeSeq);

    List<EmployeeListDTO> findEmployeeAll();

    List<EmployeeListDTO> getEmployeeSearch(@Param("searchType") String searchType, @Param("keyword") String keyword);

    String findEmployeeName(Long employeeSeq);

    ReqEmplInfoQueryDTO findMentorInfo(Long employeeSeq);

    ReqEmplInfoQueryDTO findMenteeInfo(Long employeeSeq);

    List<DepartmentListDTO> findAllDepartment();

    List<MentorDepQueryDTO> findMentorByDep(Long departmentSeq);
}
