package spring.hi_hello_spring.test.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.test.query.dto.TestAllQueryDTO;
import spring.hi_hello_spring.test.query.dto.TestQueryDTO;

import java.util.List;

@Mapper
public interface TestMapper {

    TestQueryDTO findTestById(Long testSeq);

    List<TestAllQueryDTO> findAllTests();
}
