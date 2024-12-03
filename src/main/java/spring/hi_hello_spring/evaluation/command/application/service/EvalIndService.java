package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
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

    /* 평가 지표 삭제 */
    @Transactional
    public boolean deleteEvalInd(Long evalIndSeq) {

        if(evalIndRepository.existsById(evalIndSeq)){
            evalIndRepository.deleteById(evalIndSeq);
            return true;
        }else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}
