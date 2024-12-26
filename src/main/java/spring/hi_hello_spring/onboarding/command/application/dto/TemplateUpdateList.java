package spring.hi_hello_spring.onboarding.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TemplateUpdateList {
    private List<TeamplateOrderUpdateDTO> templates;
}
