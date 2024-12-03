package spring.hi_hello_spring.quiz.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCategoryCreateDTO;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizCategoryRepository;

@Service
@RequiredArgsConstructor
public class QuizCategoryService {

    private final QuizCategoryRepository quizCategoryRepository;
    private final ModelMapper modelMapper;

    /* 퀴즈 카테고리 등록 */
    @Transactional
    public QuizCategoryCreateDTO createQuizCategory(QuizCategoryCreateDTO createDTO){

        QuizCategory quizCategory = modelMapper.map(createDTO, QuizCategory.class);
        quizCategoryRepository.save(quizCategory);

        return createDTO;
    }
}
