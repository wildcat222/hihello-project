package spring.hi_hello_spring.mentoring.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.mentoring.query.dto.ReportListQueryDTO;
import spring.hi_hello_spring.mentoring.query.dto.ResMentoringReportDTO;
import spring.hi_hello_spring.mentoring.query.mapper.ReportMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportQueryService {

    private final ReportMapper reportMapper;

    public ResMentoringReportDTO findReportDetail(Long reportSeq) {

        return reportMapper.getReportDetail(reportSeq);
    }

    public List<ReportListQueryDTO> getReportListAll() {

        return reportMapper.findAllReportListByHR();
    }

    public List<ReportListQueryDTO> getReportListByLeader() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return reportMapper.findReportListByLeader(employeeSeq);
    }

    public List<ReportListQueryDTO> getReportListByMentor() {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return reportMapper.findReportListByMentor(employeeSeq);
    }
}
