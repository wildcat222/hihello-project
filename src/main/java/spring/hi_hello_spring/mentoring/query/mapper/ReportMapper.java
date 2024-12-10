package spring.hi_hello_spring.mentoring.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;

@Mapper
public interface ReportMapper {
    ResMentoringReportDTO getReportDetail(Long reportSeq);
}
