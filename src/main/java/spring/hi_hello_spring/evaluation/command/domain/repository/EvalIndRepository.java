package spring.hi_hello_spring.evaluation.command.domain.repository;

import spring.hi_hello_spring.evaluation.command.application.dto.EvalIndCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;

public interface EvalIndRepository {

    EvalInd save(EvalInd evalInd);
}
