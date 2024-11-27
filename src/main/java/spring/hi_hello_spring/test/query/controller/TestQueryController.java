package spring.hi_hello_spring.test.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mariadb.jdbc.message.client.ResetPacket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.test.query.dto.TestQueryDTO;
import spring.hi_hello_spring.test.query.service.TestQueryService;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "99. Test API", description = "테스트용 API")
public class TestQueryController {

    private final TestQueryService testQueryService;

    /* 테스트 데이터 일부 조회*/
    @GetMapping("/{testSeq}")
    @Operation(summary = "테스트용 데이터 일부 조회", description = "테스트 데이터 일부 조회 로직 입니다.")
    public ApiResponse<?> getTestById(@PathVariable Long testSeq) {

        TestQueryDTO testQueryDTO = testQueryService.getTestById(testSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다.", testQueryDTO).getBody();
    }
}
