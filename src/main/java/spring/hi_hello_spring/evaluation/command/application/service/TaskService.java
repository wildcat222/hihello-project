package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.EvalList;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final EvalListRepository evalListRepository;
    private final TaskRepository taskRepository;
    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;

    // 과제 등록
    @Transactional
    public void createTask(TaskCreateDTO taskContent) {

        // Task 항목 저장
        Task task = new Task(taskContent);
        taskRepository.save(task);  // task 테이블에 저장 후, taskSeq가 자동 생성됨

        // EvalList 항목들 저장
        List<EvalList> evalIndicators = taskContent.getEvalIndicators().stream()
                .map(dto -> {
                    EvalList evalList = modelMapper.map(dto, EvalList.class);
                    evalList.updateTaskSeq(task.getTaskSeq());  // taskSeq 값을 설정
                    return evalList;
                })
                .collect(Collectors.toList());
        evalListRepository.saveAll(evalIndicators);  // eval_list 테이블에 저장

        // Template 항목 수정
        Template template = templateRepository.findByTemplateTaskRound(
                taskContent.getTemplateTaskRound()
        );
        if (template == null) {
            throw new RuntimeException("Template not found");
        }

        // Template 업데이트 (DTO에 기반하여 업데이트)
        template.updateTemplate(taskContent.getTemplateUrl());
        templateRepository.save(template);  // template 테이블에 수정된 내용 저장
    }

    // 과제 수정

    // 과제 삭제
}
