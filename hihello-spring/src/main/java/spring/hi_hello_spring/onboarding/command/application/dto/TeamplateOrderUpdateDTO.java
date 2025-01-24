package spring.hi_hello_spring.onboarding.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TeamplateOrderUpdateDTO {
    private Long templateSeq; // 템플릿의 고유 ID
    private Integer templateProcedure; // 템플릿 순서 정보
}
