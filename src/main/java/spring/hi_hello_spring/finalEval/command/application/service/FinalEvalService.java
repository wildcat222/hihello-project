package spring.hi_hello_spring.finalEval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.*;
import spring.hi_hello_spring.evaluation.command.domain.repository.*;
import spring.hi_hello_spring.finalEval.command.application.dto.FinalEvalCreateDTO;
import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEval;
import spring.hi_hello_spring.finalEval.command.domain.aggregate.entity.FinalEvalInd;
import spring.hi_hello_spring.finalEval.command.domain.repository.FinalEvalIndRepository;
import spring.hi_hello_spring.finalEval.command.domain.repository.FinalEvalRepository;
import spring.hi_hello_spring.group.command.domain.repository.PeerReviewRepository;
import spring.hi_hello_spring.mentoring.command.domain.repository.ReportRepository;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;
import spring.hi_hello_spring.quiz.command.domain.repository.QuizResultRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalEvalService {

    private final FinalEvalRepository finalEvalRepository;
    private final FinalEvalIndRepository finalEvalIndRepository;
    private final EvalIndRepository evalIndRepository;
    private final TemplateRepository templateRepository;
    private final QuizResultRepository quizResultRepository;
    private final ReportRepository reportRepository;
    private final PeerReviewRepository peerReviewRepository;
    private final ModelMapper modelMapper;
    private final TaskEvalRepository taskEvalRepository;

    @Transactional
    public void createFinalEval(Long employeeSeq, List<FinalEvalCreateDTO> finalEvalCreateDTOs) {
        for (FinalEvalCreateDTO finalEvalCreateDTO : finalEvalCreateDTOs) {
            FinalEval finalEval = modelMapper.map(finalEvalCreateDTO, FinalEval.class);
            finalEval.updateEmployeeSeq(employeeSeq);

            Long evalIndSeq = finalEval.getEvalIndSeq();
            Long finalEvalIndSeq = finalEval.getFinalEvalIndSeq();

            double finalScore = calculateFinalScore(employeeSeq, evalIndSeq, finalEvalIndSeq);

            finalEval.updateEmployeeScore(Math.round(finalScore * 10) / 10.0);  // 최종점수는 소수 첫째자리까지 반올림
            finalEvalRepository.save(finalEval);
        }
    }

    // 최종 점수를 반환하는 메서드
    private double calculateFinalScore(Long employeeSeq, Long evalIndSeq, Long finalEvalIndSeq) {
        double finalScore = 0;

        if (evalIndSeq == null) {
            finalScore = calculateScoreForFinalEvalInd(employeeSeq, finalEvalIndSeq);
        } else if (finalEvalIndSeq == null) {
            finalScore = calculateScoreForEvalInd(employeeSeq, evalIndSeq);
        }

        return finalScore;
    }

    // 과제 평가 지표와 별도의 평가(퀴즈, 보고서, 동료평가에 대한 점수 계산)
    private double calculateScoreForFinalEvalInd(Long employeeSeq, Long finalEvalIndSeq) {
        FinalEvalInd finalEvalInd = finalEvalIndRepository.findById(finalEvalIndSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        int indWeight = finalEvalInd.getFinalEvalIndWeight();
        Integer totalScore = 0;
        Integer employeeScore = switch (finalEvalInd.getFinalEvalIndName()) {
            case "퀴즈" -> {
                totalScore = templateRepository.countTotalQuizQty();
                yield quizResultRepository.countByEmployeeSeqAndQuizCorrectStatus(employeeSeq, true).intValue();
            }
            case "멘토링 보고서" -> {
                totalScore = reportRepository.maxReportWeek(employeeSeq);
                yield reportRepository.countReportSubmittedQtyByMenteeSeq(employeeSeq);
            }
            case "동료 평가" -> {
                totalScore = peerReviewRepository.findTotalPeerReviewPerfectScoreByRevieweeSeq(employeeSeq);
                yield peerReviewRepository.sumPeerReviewScoreByRevieweeSeq(employeeSeq);
            }
            default -> throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        };

        if (employeeScore == null || totalScore == null) {
            return 0;
        }

        return ((double) employeeScore / (double) totalScore) * indWeight;
    }

    // 과제의 각 평가 지표별 점수 계산
    private double calculateScoreForEvalInd(Long employeeSeq, Long evalIndSeq) {
        EvalInd evalInd = evalIndRepository.findById(evalIndSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        int totalTaskQty = templateRepository.countByTemplateTaskRoundIsNotNull().intValue();
        int perfectScoreOfEvalInd = evalInd.getEvalIndScore();
        double totalPerfectScoreOfEvalInd = perfectScoreOfEvalInd * totalTaskQty;

        // 그룹 과제일 경우 해당 평가 지표에 대한 사원 점수 계산
        Integer totalGroupScoreByEmployeeSeqAndEvalIndSeq = taskEvalRepository.sumGroupTaskScoreByTaskSubmitSeqAndEvalIndSeq(employeeSeq, evalIndSeq);
        totalGroupScoreByEmployeeSeqAndEvalIndSeq = totalGroupScoreByEmployeeSeqAndEvalIndSeq != null ? totalGroupScoreByEmployeeSeqAndEvalIndSeq : 0;

        // 개인 과제일 경우 해당 평가 지표에 대한 사원 점수 계산
        Integer totalScoreByEmployeeSeqAndEvalIndSeq = taskEvalRepository.sumTaskScoreByEmployeeSeqAndEvalIndSeq(employeeSeq, evalIndSeq);
        totalScoreByEmployeeSeqAndEvalIndSeq = totalScoreByEmployeeSeqAndEvalIndSeq != null ? totalScoreByEmployeeSeqAndEvalIndSeq : 0;

        double totalTaskScoreByEmployeeAndEvalInd = totalGroupScoreByEmployeeSeqAndEvalIndSeq + totalScoreByEmployeeSeqAndEvalIndSeq;

        Integer sumFinalEvalIndWeight = finalEvalIndRepository.sumFinalEvalIndWeight();
        sumFinalEvalIndWeight = sumFinalEvalIndWeight != null ? sumFinalEvalIndWeight : 0;

        int countEvalInd = evalIndRepository.countBy().intValue();

        return (((double) (100 - sumFinalEvalIndWeight) / (double) countEvalInd) * totalTaskScoreByEmployeeAndEvalInd) / totalPerfectScoreOfEvalInd;
    }
}