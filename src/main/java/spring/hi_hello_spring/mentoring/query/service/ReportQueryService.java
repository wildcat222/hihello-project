package spring.hi_hello_spring.mentoring.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;
import spring.hi_hello_spring.mentoring.query.mapper.ReportMapper;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private final ReportMapper reportMapper;

    public ResMentoringReportDTO findReportDetail(Long reportSeq) {

        return reportMapper.getReportDetail(reportSeq);
    }

}
