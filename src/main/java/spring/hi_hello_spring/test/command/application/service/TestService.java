package spring.hi_hello_spring.test.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.test.command.application.dto.TestCreateReqDTO;
import spring.hi_hello_spring.test.command.application.dto.TestUpdateReqDTO;
import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;
import spring.hi_hello_spring.test.command.domain.repository.TestRepository;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    /* Test 추가 작업 */
    @Transactional
    public TestCreateReqDTO createTest(TestCreateReqDTO testReqDTO){
        Test test = modelMapper.map(testReqDTO, Test.class);
        testRepository.save(test);
        return testReqDTO;
    }

    /* Test 데이터 수정 작업 */
    @Transactional
    public TestUpdateReqDTO updateTest(Long testSeq, TestUpdateReqDTO testUpdateReqDTO){

        Test test = testRepository.findById(testSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        return modelMapper.map(test, TestUpdateReqDTO.class);
    }

    /* Test 데이터 삭제 작업 */
    @Transactional
    public boolean deleteTest(Long testSeq){
        if(testRepository.existsById(testSeq)){
            testRepository.deleteById(testSeq);
            return true;
        }else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}
