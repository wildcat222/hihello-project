package spring.hi_hello_spring.onboarding.command.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateDomainService {

    private final TemplateRepository templateRepository;

    @Transactional
    public void updateTask(TaskCreateDTO taskContent) {
        Template template = templateRepository.findByTemplateTaskRound(
                taskContent.getTemplateTaskRound()
        );
        if (template == null) {
            throw new CustomException(ErrorCodeType.TEMPLATE_NOT_FOUND);
        }

        Template updatedTemplate = Template.builder()
                .templateUrl(taskContent.getTaskUrl())
                .build();

        templateRepository.save(template);
    }
}
