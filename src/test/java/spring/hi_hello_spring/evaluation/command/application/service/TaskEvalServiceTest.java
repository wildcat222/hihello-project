package spring.hi_hello_spring.evaluation.command.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalGroupCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskGroupEvalRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class TaskEvalServiceTest {

    @Mock
    private TaskGroupEvalRepository taskGroupEvalRepository;

    @Mock
    private CustomUserUtils customUserUtils;

    @InjectMocks
    private TaskEvalService taskEvalService;

    @Test
    void createTaskGroupEval_whenValidDTOProvided_shouldSavePeerReviews() {
        // Given
        EvalGroupCreateDTO dto1 = new EvalGroupCreateDTO();
        dto1.setPeerReviewListSeq(1L);
        dto1.setRevieweeSeq(2L);
        dto1.setPeerReviewScore(85);

        EvalGroupCreateDTO dto2 = new EvalGroupCreateDTO();
        dto2.setPeerReviewListSeq(2L);
        dto2.setRevieweeSeq(3L);
        dto2.setPeerReviewScore(90);

        List<EvalGroupCreateDTO> evalGroupCreateDTOList = Arrays.asList(dto1, dto2);

        Long reviewerSeq = 100L;

        // When
        taskEvalService.createTaskGroupEval(evalGroupCreateDTOList);

        // Then
        Mockito.verify(taskGroupEvalRepository, Mockito.times(1)).saveAll(anyList());
    }
}
