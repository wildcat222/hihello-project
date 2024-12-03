package spring.hi_hello_spring.quiz.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCategoryCreateDTO;
import spring.hi_hello_spring.quiz.command.application.service.QuizCategoryService;

@RestController
@RequestMapping("api/v1/hr/quiz/category")
@RequiredArgsConstructor
@Tag(name = "QuizCategory API", description = "퀴즈 카테고리 관련 API")
public class QuizCategoryController {

    private final QuizCategoryService quizCategoryService;

    /* 퀴즈 카테고리 등록 */
    @PostMapping
    @Operation(summary = "퀴즈 카테고리 생성", description = "퀴즈 카테고리 생성 로직입니다.")
    public ApiResponse<?> createQuizCategory(QuizCategoryCreateDTO createDTO){

        quizCategoryService.createQuizCategory(createDTO);
        return ResponseUtil.successResponse("퀴즈 카테고리가 성공적으로 등록되었습니다.").getBody();
    }
}
