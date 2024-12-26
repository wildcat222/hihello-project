package spring.hi_hello_spring.finalEval.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalDetailQueryDTO;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalResultQueryDTO;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalsQueryDTO;
import spring.hi_hello_spring.finalEval.query.mapper.FinalEvalMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalEvalQueryService {

    private final FinalEvalMapper finalEvalMapper;

    public List<FinalEvalsQueryDTO> getAllFinalEvals() {
        return finalEvalMapper.findAllFinalEvals();
    }

    public FinalEvalDetailQueryDTO getFinalEvalDetail(Long employeeSeq) {
        FinalEvalDetailQueryDTO finalEvalDetailQueryDTO = finalEvalMapper.findEmployeeInfo(employeeSeq);
        List<FinalEvalResultQueryDTO> finalEvalResultQueryDTOS = finalEvalMapper.findFinalEvalResult(employeeSeq);
        finalEvalDetailQueryDTO.setFinalEvalResults(finalEvalResultQueryDTOS);
        return finalEvalDetailQueryDTO;
    }

    public List<FinalEvalsQueryDTO> searchFinalEvals(String keyword) {
        return finalEvalMapper.searchFinalEvals(keyword);
    }
}