package spring.hi_hello_spring.wiki.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiSnapshotRepository;

public interface JpaWikiSnapshotRepository extends WikiSnapshotRepository, JpaRepository<WikiSnapshot, Long> {
}