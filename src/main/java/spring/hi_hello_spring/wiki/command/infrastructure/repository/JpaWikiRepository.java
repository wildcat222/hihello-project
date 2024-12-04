package spring.hi_hello_spring.wiki.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiRepository;

public interface JpaWikiRepository extends WikiRepository, JpaRepository<Wiki, Long> {
}