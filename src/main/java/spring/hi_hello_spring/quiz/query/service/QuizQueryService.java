package spring.hi_hello_spring.quiz.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.quiz.query.dto.QuizAllQueryDTO;
import spring.hi_hello_spring.quiz.query.mapper.QuizMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQueryService {

    private final QuizMapper quizMapper;

    /* 카테고리 별 퀴즈 전체 조회 */
    public List<QuizAllQueryDTO> getCategoryQuiz(Long quizCategorySeq){

        /* 유효성 검사는 어떻게 할지? */

        return quizMapper.findAllCategoryQuiz(quizCategorySeq);
    }
}
