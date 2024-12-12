package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalGroupCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskEvalCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskEval;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskEvalRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskGroupEvalRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskSubmitRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.PeerReview;
import spring.hi_hello_spring.group.command.domain.repository.PeerReviewRepository;
import spring.hi_hello_spring.test.command.domain.aggregate.entity.Test;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskEvalService {

    private final TaskSubmitRepository taskSubmitRepository;
    private final TaskEvalRepository taskEvalRepository;
    private final TaskGroupEvalRepository taskGroupEvalRepository;
    private final EvalListRepository evalListRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createTaskEval(Long taskSubmitSeq, List<TaskEvalCreateDTO> taskEvalCreateDTOS) {
        // taskSubmitSeq에 대한 데이터가 존재하는지 확인
        if (!taskSubmitRepository.existsById(taskSubmitSeq)) {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }

        for (TaskEvalCreateDTO taskEvalCreateDTO : taskEvalCreateDTOS) {
            Long evalListSeq = taskEvalCreateDTO.getEvalListSeq();

            // evalListSeq에 대한 데이터가 존재하는지 확인
            EvalList evalList = evalListRepository.findById(evalListSeq)
                    .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

            // 제출된 과제에서 해당 평가 항목에 대한 평가가 이미 존재하는지 확인
            if (taskEvalRepository.existsByTaskSubmitSeqAndEvalListSeq(taskSubmitSeq, evalListSeq)) {
                throw new CustomException(ErrorCodeType.DUPLICATE_DATA);
            }

            int taskScore = taskEvalCreateDTO.getTaskScore();
            int maxTaskScore = evalList.getEvalListScore();

            // 점수가 유효한 값인지 확인
            if (taskScore < 0 || taskScore > maxTaskScore) {
                throw new CustomException(ErrorCodeType.INVALID_VALUE);
            }

            TaskEval taskEval = TaskEval.builder()
                    .evalListSeq(evalListSeq)
                    .taskSubmitSeq(taskSubmitSeq)
                    .taskScore(taskScore)
                    .build();

            taskEvalRepository.save(taskEval);
        }
    }

    @Transactional
    public void createTaskGroupEval(List<EvalGroupCreateDTO> evalGroupCreateDTO) {
        Long reviewerSeq = CustomUserUtils.getCurrentEmployeeSeq();

        // 빌더를 사용하여 DTO -> Entity 변환
        List<PeerReview> peerReviews = evalGroupCreateDTO.stream()
                .map(dto -> PeerReview.builder()
                        .peerReviewListSeq(dto.getPeerReviewListSeq())
                        .reviewerSeq(reviewerSeq) // reviewerSeq 직접 설정
                        .revieweeSeq(dto.getRevieweeSeq())
                        .peerReviewScore(dto.getPeerReviewScore())
                        .build())
                .collect(Collectors.toList());

        // 배치 저장
        taskGroupEvalRepository.saveAll(peerReviews);
    }
}