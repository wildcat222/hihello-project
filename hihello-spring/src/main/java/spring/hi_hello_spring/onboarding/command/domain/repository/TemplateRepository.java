package spring.hi_hello_spring.onboarding.command.domain.repository;

import org.springframework.data.jpa.repository.Query;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.TemplateType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TemplateRepository {

    Template save(Template template);

    boolean existsById(Long templateSeq);

    void deleteById(Long templateSeq);

    Optional<Template> findById(Long templateSeq);

    @Query("SELECT SUM(templateQuizQty) FROM Template WHERE templateType = 'QUIZ'")
    Integer countTotalQuizQty();

    Long countByTemplateTaskRoundIsNotNull();

    Template findByTemplateSeq(Long templateSeq);

    List<Template> findAll();

    Collection<Object> findByTemplateType(TemplateType templateType);

    Template findFirstByOrderByTemplateEndAtDesc();
}
