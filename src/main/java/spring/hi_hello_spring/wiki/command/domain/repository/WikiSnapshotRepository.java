package spring.hi_hello_spring.wiki.command.domain.repository;

import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;

public interface WikiSnapshotRepository {

    WikiSnapshot save(WikiSnapshot wikiSnapshot);
}