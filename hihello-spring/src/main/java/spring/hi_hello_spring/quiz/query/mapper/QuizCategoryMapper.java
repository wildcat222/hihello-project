package spring.hi_hello_spring.quiz.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.quiz.query.dto.QuizCategoryAllQueryDTO;

import java.util.List;

@Mapper
public interface QuizCategoryMapper {
    List<QuizCategoryAllQueryDTO> findAllQuizCategory();
}
