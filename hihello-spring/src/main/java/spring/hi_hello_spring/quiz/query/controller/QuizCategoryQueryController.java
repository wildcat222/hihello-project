package spring.hi_hello_spring.quiz.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.query.dto.QuizCategoryAllQueryDTO;
import spring.hi_hello_spring.quiz.query.service.QuizCategoryQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/quizCategory")
@RequiredArgsConstructor
@Tag(name = "QuizCategory API", description = "퀴즈 카테고리 관련 API")
public class QuizCategoryQueryController {

    private final QuizCategoryQueryService quizCategoryQueryService;

    /* 퀴즈 카테고리 리스트 조회 */
    @GetMapping
    @Operation(summary = "퀴즈 카테고리 조회", description = "퀴즈 카테고리 조회 로직입니다.")
    public ApiResponse<?> getAllQuizCategory(){

        List<QuizCategoryAllQueryDTO> queryDTO = quizCategoryQueryService.getAllQuizCategory();
        return ResponseUtil.successResponse("퀴즈 카테고리 리스트를 성공적으로 조회했습니다.", queryDTO).getBody();
    }
}
