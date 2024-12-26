package spring.hi_hello_spring.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.query.dto.EvalListsQueryDTO;
import spring.hi_hello_spring.evaluation.query.mapper.EvalListMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvalListQueryService {

    private final EvalListMapper evalListMapper;

    public List<EvalListsQueryDTO> getEvalListsByTaskSeq(Long taskSeq) {
        return evalListMapper.findEvalListsByTaskSeq(taskSeq);
    }
}