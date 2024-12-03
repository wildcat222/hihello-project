package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalIndCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalInd;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalIndRepository;

@Service
@RequiredArgsConstructor
public class EvalIndService {

    private final EvalIndRepository evalIndRepository;
    private final ModelMapper modelMapper;

    /* 평가 지표 등록 */
    @Transactional
    public EvalIndCreateDTO createEvalInd(EvalIndCreateDTO evalIndCreateDTO) {

        EvalInd evalInd = modelMapper.map(evalIndCreateDTO, EvalInd.class);
        evalIndRepository.save(evalInd);

        return evalIndCreateDTO;
    }
}
