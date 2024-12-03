package spring.hi_hello_spring.quiz.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCategoryCreateDTO;
import spring.hi_hello_spring.quiz.command.application.service.QuizCategoryService;

@RestController
@RequestMapping("api/v1/hr/quizCategory")
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

    /* 퀴즈 카테고리 삭제 */
    @DeleteMapping("/{quizCategorySeq}")
    @Operation(summary = "퀴즈 카테고리 삭제", description = "퀴즈 카테고리 삭제 로직입니다.")
    public ApiResponse<?> deleteQuizCategory(@PathVariable Long quizCategorySeq){

        quizCategoryService.deleteQuizCategory(quizCategorySeq);
        return ResponseUtil.successResponse("퀴즈 카테고리가 성공적으로 삭제되었습니다.").getBody();
    }
}
