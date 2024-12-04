package spring.hi_hello_spring.wiki.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiModContentRepository;

public interface JpaWikiModContentRepository extends WikiModContentRepository, JpaRepository<WikiModContent, Long> {
}