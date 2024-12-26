package spring.hi_hello_spring.evaluation.command.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskUpdateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvalListDomainService {

    private final ModelMapper modelMapper;
    private final EvalListRepository evalListRepository;

    @Transactional
    public void createTask(TaskCreateDTO taskContent, Task task) {

        List<EvalList> evalIndicators = taskContent.getEvalIndicators().stream()
                .map(dto -> EvalList.builder()
                        .taskSeq(task.getTaskSeq())
                        .evalIndSeq(dto.getEvalIndSeq())
                        .evalListContent(dto.getEvalListContent())
                        .evalListScore(dto.getEvalListScore())
                        .build())
                        .collect(Collectors.toList());

        evalListRepository.saveAll(evalIndicators);// 저장
    }

    @Transactional
    public void updateTask(TaskUpdateDTO taskUpdateDTO, Task task) {
        List<EvalList> evalIndicators = taskUpdateDTO.getEvalIndicators().stream()
                .map(dto -> EvalList.builder()
                        .taskSeq(task.getTaskSeq())
                        .evalListSeq(dto.getEvalListSeq())
                        .evalListContent(dto.getEvalListContent())
                        .evalListScore(dto.getEvalListScore())
                        .evalIndSeq(dto.getEvalIndSeq())
                        .build())
                .collect(Collectors.toList());

        evalListRepository.saveAll(evalIndicators);// 저장
    }
}
