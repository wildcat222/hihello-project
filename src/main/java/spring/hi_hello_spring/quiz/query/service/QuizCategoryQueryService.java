package spring.hi_hello_spring.quiz.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.quiz.query.dto.QuizCategoryAllQueryDTO;
import spring.hi_hello_spring.quiz.query.mapper.QuizCategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizCategoryQueryService {

    private final QuizCategoryMapper quizCategoryMapper;

    /* 퀴즈 카테고리 리스트 조회 */
    public List<QuizCategoryAllQueryDTO> getAllQuizCategory(){

        return quizCategoryMapper.findAllQuizCategory();
    }
}
