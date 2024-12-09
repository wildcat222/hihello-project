package spring.hi_hello_spring.mentoring.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Report;

import java.util.Optional;

public interface ReportRepository {

    @Query("SELECT MAX(reportWeek) FROM Report")
    Integer maxReportWeek(@Param("menteeSeq") Long menteeSeq);

    @Query("SELECT COUNT(*) FROM Report r JOIN Mentoring m ON r.mentoringSeq = m.mentoringSeq WHERE m.menteeSeq = :menteeSeq")
    int countReportSubmittedQtyByMenteeSeq(@Param("menteeSeq") Long menteeSeq);

    Report save(Report report);

    Optional<Report> findByMentoringSeqOrderByReportWeekDesc(Long mentoringSeq);
}