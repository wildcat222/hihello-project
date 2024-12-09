package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteFeedbackDTO;
import spring.hi_hello_spring.mentoring.command.application.dto.WriteReportDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Report;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.mentoring.command.domain.repository.ReportRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    public final MentoringRepository mentoringRepository;
    public final ReportRepository reportRepository;
    public final ModelMapper modelMapper;

    @Transactional
    public void createReport(Long employeeSeq, WriteReportDTO writeReportDTO) {

        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);
        Report report = modelMapper.map(writeReportDTO, Report.class);

        Optional<Report> recentReportOpt = reportRepository.findByMentoringSeqOrderByReportWeekDesc(mentoring.getMentoringSeq());

        int week = recentReportOpt.map(Report::getReportWeek).orElse(0) + 1;
        report.forGroup(mentoring.getMentoringSeq(), week);

        reportRepository.save(report);
    }

    @Transactional
    public void modifyReport(Long employeeSeq, Long reportSeq, WriteReportDTO writeReportDTO) {

        Mentoring mentoring = mentoringRepository.findByMenteeSeq(employeeSeq);

        Report report = reportRepository.findByReportSeq(reportSeq);

        if (Objects.equals(report.getMentoringSeq(), mentoring.getMentoringSeq())) {
            modelMapper.map(writeReportDTO, report);
            reportRepository.save(report);
        } else {
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }

    }

    @Transactional
    public void writeFeedback(Long employeeSeq, Long reportSeq, WriteFeedbackDTO writeFeedbackDTO) {

        Report report = reportRepository.findByReportSeq(reportSeq);

        Mentoring mentoring = mentoringRepository.findByMentoringSeq(report.getMentoringSeq());

        if (Objects.equals(employeeSeq, mentoring.getMentorSeq())) {
            modelMapper.map(writeFeedbackDTO, report);
            reportRepository.save(report);
        } else {
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }

    }
}
