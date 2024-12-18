package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.EvalListsQueryDTO;

import java.util.List;

@Mapper
public interface EvalListMapper {

    List<EvalListsQueryDTO> findEvalListsByTaskSeq(Long taskSeq);
}