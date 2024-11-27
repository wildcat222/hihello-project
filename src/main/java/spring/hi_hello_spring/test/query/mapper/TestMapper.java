package spring.hi_hello_spring.test.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.test.query.dto.TestQueryDTO;

@Mapper
public interface TestMapper {

    TestQueryDTO findTestById(Long testSeq);
}
