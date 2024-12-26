package spring.hi_hello_spring.onboarding.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class CompletedStatusDTO {

    private int totalCount;
    private int completedCount;
}
