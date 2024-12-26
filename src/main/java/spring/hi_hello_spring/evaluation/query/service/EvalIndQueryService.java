package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.EvalIndAllQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.EvalIndQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvalIndQueryService {

    private final EvalIndQueryMapper evalIndMapper;

    /* 평가 지표 조회 */
    public List<EvalIndAllQueryDTO> findAllEvalInd() {

        return evalIndMapper.findAllEvalIndList();
    }
}
