package spring.hi_hello_spring.wiki.command.domain.repository;

import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;

public interface WikiModContentRepository {
    WikiModContent save(WikiModContent wikiModContent);
}