package spring.hi_hello_spring.test.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.test.command.application.dto.TestCreateReqDTO;
import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;
import spring.hi_hello_spring.test.command.domain.repository.TestRepository;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    /* Test 추가 작업 */
    public TestCreateReqDTO createTest(TestCreateReqDTO testReqDTO){
        Test test = modelMapper.map(testReqDTO, Test.class);
        testRepository.save(test);
        return testReqDTO;
    }
}
