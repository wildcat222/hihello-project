package spring.hi_hello_spring.finalEval.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalDetailQueryDTO;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalResultQueryDTO;
import spring.hi_hello_spring.finalEval.query.dto.FinalEvalsQueryDTO;

import java.util.List;

@Mapper
public interface FinalEvalMapper {

    List<FinalEvalsQueryDTO> findAllFinalEvals();
    List<FinalEvalsQueryDTO> searchFinalEvals(String keyword);
    FinalEvalDetailQueryDTO findEmployeeInfo(Long employeeSeq);
    List<FinalEvalResultQueryDTO> findFinalEvalResult(Long employeeSeq);
}