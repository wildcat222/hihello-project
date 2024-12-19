package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;


public interface CheckListStatusRepository {

    CheckListStatus save(CheckListStatus checkListStatus);

}
