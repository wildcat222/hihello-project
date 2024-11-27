package spring.hi_hello_spring.test.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.test.query.dto.TestQueryDTO;
import spring.hi_hello_spring.test.query.mapper.TestMapper;

@Service
@RequiredArgsConstructor
public class TestQueryService {

    private final TestMapper testMapper;

    /* 특정 ID로 Test 데이터를 조회하는 메서드 */
    public TestQueryDTO getTestById(Long testSeq) {

        TestQueryDTO testQueryDTO = testMapper.findTestById(testSeq);

        if(testQueryDTO == null) {
            throw new IllegalArgumentException("해당 ID의 데이터가 존재하지 않습니다 : " + testSeq);
        }
        return testQueryDTO;
    }
}
