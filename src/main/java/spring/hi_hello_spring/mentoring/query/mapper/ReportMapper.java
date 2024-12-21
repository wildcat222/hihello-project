package spring.hi_hello_spring.mentoring.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.hi_hello_spring.mentoring.query.dto.MenteeReportListQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.ReportListQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;

import java.util.List;

@Mapper
public interface ReportMapper {

    ResMentoringReportDTO getReportDetail(Long reportSeq);

    List<ReportListQueryDTO> findAllReportListByHR();

    List<ReportListQueryDTO> findReportListByLeader(Long employeeSeq);

    List<ReportListQueryDTO> findReportListByMentor(Long employeeSeq);

    List<MenteeReportListQueryDTO> findReportListByMentee(Long employeeSeq);

    List<MenteeReportListQueryDTO> getReportSearch(@Param("searchType") String searchType, @Param("keyword") String keyword);

    List<MenteeReportListQueryDTO> getReportSearchByLeader(@Param("employeeSeq") Long employeeSeq, @Param("searchType") String searchType, @Param("keyword") String keyword);
}
