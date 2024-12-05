package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;

public interface TemplateRepository {
    Template save(Template template);

    boolean existsById(Long templateSeq);

    void deleteById(Long templateSeq);
}
