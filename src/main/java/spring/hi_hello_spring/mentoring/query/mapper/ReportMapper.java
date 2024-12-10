package spring.hi_hello_spring.mentoring.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.mentoring.query.dto.ReportListQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;

import java.util.List;

@Mapper
public interface ReportMapper {
    ResMentoringReportDTO getReportDetail(Long reportSeq);

    List<ReportListQueryDTO> findAllReportListByHR();
}
