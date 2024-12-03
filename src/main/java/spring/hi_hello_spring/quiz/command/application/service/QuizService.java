package spring.hi_hello_spring.quiz.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCreateDTO;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.Quiz;
import spring.hi_hello_spring.quiz.command.domain.aggregate.entity.QuizCategory;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizCategoryRepository;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCategoryRepository quizCategoryRepository;
    private final ModelMapper modelMapper;

    /* 카테고리 별 퀴즈 등록 */
    public QuizCreateDTO createQuiz(Long quizCategorySeq, QuizCreateDTO createDTO) {

        QuizCategory quizCategory = quizCategoryRepository.findById(quizCategorySeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        Quiz quiz = modelMapper.map(createDTO, Quiz.class);

        quiz.Category(quizCategory);

        Quiz savedQuiz = quizRepository.save(quiz);

        return modelMapper.map(savedQuiz, QuizCreateDTO.class);
    }
}
