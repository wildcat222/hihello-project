package spring.hi_hello_spring.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.common.aggregate.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
    void deleteByPlanningSeq(Long planningSeq);
}
