package spring.hi_hello_spring.onboarding.command.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CheckListCreateDTO {

    private String checklistContent;

    @JsonCreator
    public CheckListCreateDTO(@JsonProperty("checklistContent") String checklistContent) {
        this.checklistContent = checklistContent;
    }
}
