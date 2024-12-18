package spring.hi_hello_spring.onboarding.command.domain.repository;

import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;


public interface CheckListRepository {

    CheckListStatus save(CheckListStatus checkListStatus);
}
