package spring.hi_hello_spring.quiz.command.domain.repository;

public interface QuizResultRepository {

    Long countByEmployeeSeqAndQuizCorrectStatus(Long employeeSeq, boolean quizCorrectStatus);
}