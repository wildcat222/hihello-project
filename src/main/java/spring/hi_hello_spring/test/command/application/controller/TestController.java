package spring.hi_hello_spring.test.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.s3.FileUploadUtil;
import spring.hi_hello_spring.test.command.application.dto.TestCreateReqDTO;
import spring.hi_hello_spring.test.command.application.dto.TestUpdateReqDTO;
import spring.hi_hello_spring.test.command.application.service.TestService;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
@Tag(name = "99. Test API", description = "테스트용 API")
public class TestController {

    private final TestService testService;
    private final FileUploadUtil fileUploadUtil;

    /* 테스트 데이터 생성 로직 */
    @PostMapping
    @Operation(summary = "테스트용 데이터 생성", description = "테스트 데이터 생성 로직 입니다.")
    public ApiResponse<?> createTest(@RequestBody TestCreateReqDTO reqDTO){

        testService.createTest(reqDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 생성되었습니다.").getBody();
    }

    @PutMapping("/{testSeq}")
    @Operation(summary = "테스트용 데이터 수정", description = "테스트 데이터 수정 로직 입니다.")
    public ApiResponse<?> updateTest(@PathVariable Long testSeq, @RequestBody TestUpdateReqDTO reqDTO){
        testService.updateTest(testSeq, reqDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 수정되었습니다.").getBody();
    }

}
