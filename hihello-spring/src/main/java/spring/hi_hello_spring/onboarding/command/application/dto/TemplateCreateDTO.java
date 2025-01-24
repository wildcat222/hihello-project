package spring.hi_hello_spring.onboarding.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.TemplateType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TemplateCreateDTO {

    private Long quizCategorySeq;
    private TemplateType templateType;
    private Boolean templateCheckRequiredStatus;
    private String templateTrainingType;
    private String templateTitle;
    private String templateSub;
    private String templateDetail;
    private String templateUrlName;
    private int templateQuizQty;
    private String templateTaskRound;
    private int templateProcedure;
    private LocalDateTime templateEndAt;
    private List<CheckListCreateDTO> checklistContent;
}
