package spring.hi_hello_spring.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.repository.EvalListRepository;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;
import spring.hi_hello_spring.evaluation.command.domain.service.EvalListDomainService;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;
import spring.hi_hello_spring.onboarding.command.domain.service.TemplateDomainService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final EvalListRepository evalListRepository;
    private final TaskRepository taskRepository;
    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;
    private final EvalListDomainService evalListDomainService;
    private final TemplateDomainService templateDomainService;

    // 과제 등록
    @Transactional
    public void createTask(TaskCreateDTO taskContent) {

        Task task = modelMapper.map(taskContent, Task.class);
        Template template = templateRepository.findByTemplateTaskRound(taskContent.getTemplateTaskRound());
        task.updateTemplateSeq(template.getTemplateSeq());
        taskRepository.save(task);

        // EvalList 항목들 저장
        evalListDomainService.createTask(taskContent,task);

        // Template 항목 수정
        templateDomainService.updateTask(taskContent);

    }

    // 과제 수정

    // 과제 삭제
}
