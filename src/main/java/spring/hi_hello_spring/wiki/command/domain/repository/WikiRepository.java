package spring.hi_hello_spring.wiki.command.domain.repository;

import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;

public interface WikiRepository {
    Wiki save(Wiki wiki);
}