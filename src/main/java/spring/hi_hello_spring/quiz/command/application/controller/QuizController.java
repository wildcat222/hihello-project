package spring.hi_hello_spring.quiz.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCreateDTO;
import spring.hi_hello_spring.quiz.command.application.service.QuizService;

@RestController
@RequestMapping("api/v1/hr/quizCategory")
@RequiredArgsConstructor
@Tag(name = "Quiz API", description = "퀴즈 관련 API")
public class QuizController {

    private final QuizService quizService;

    /* 카테고리 별 퀴즈 등록 */
    @PostMapping("/{quizCategorySeq}/quiz")
    @Operation(summary = "카테고리 별 퀴즈 생성", description = "카테고리별 퀴즈 생성 로직입니다.")
    public ApiResponse<?> createQuiz(@PathVariable Long quizCategorySeq, QuizCreateDTO quizCreateDTO){

        quizService.createQuiz(quizCategorySeq, quizCreateDTO);
        return ResponseUtil.successResponse("퀴즈가 성공적으로 추가 되었습니다.").getBody();
    }
}
