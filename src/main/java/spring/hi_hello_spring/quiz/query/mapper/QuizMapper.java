package spring.hi_hello_spring.quiz.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.quiz.query.dto.QuizAllQueryDTO;

import java.util.List;

@Mapper
public interface QuizMapper {
    List<QuizAllQueryDTO> findAllCategoryQuiz(Long quizCategorySeq);
}
