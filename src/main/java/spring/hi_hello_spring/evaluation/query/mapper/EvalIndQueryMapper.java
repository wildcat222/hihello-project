package spring.hi_hello_spring.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.evaluation.query.dto.EvalIndAllQueryDTO;

import java.util.List;

@Mapper
public interface EvalIndQueryMapper {

    List<EvalIndAllQueryDTO> findAllEvalIndList();
}
