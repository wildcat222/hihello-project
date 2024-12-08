package spring.hi_hello_spring.mentoring.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Report;
import spring.hi_hello_spring.mentoring.command.domain.repository.ReportRepository;

public interface JpaReportRepository extends ReportRepository, JpaRepository<Report, Long> {
}