package spring.hi_hello_spring.quiz.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.query.dto.QuizAllQueryDTO;
import spring.hi_hello_spring.quiz.query.service.QuizQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/quizCategory")
@RequiredArgsConstructor
@Tag(name = "Quiz API", description = "퀴즈 관련 API")
public class QuizQueryController {

    private final QuizQueryService quizQueryService;

    /* 카테고리 별 퀴즈 전체 조회*/
    @GetMapping("/{quizCategorySeq}/quiz")
    @Operation(summary = "카테고리 별 퀴즈 조회", description = "카테고리별 퀴즈 조회 로직입니다.")
    public ApiResponse<?> getAllCategoryQuiz(@PathVariable Long quizCategorySeq){

        List<QuizAllQueryDTO> queryDTO = quizQueryService.getCategoryQuiz(quizCategorySeq);
        return ResponseUtil.successResponse("퀴즈가 성공적으로 조회되었습니다.", queryDTO).getBody();
    }
}
