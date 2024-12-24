package spring.hi_hello_spring.notify.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotiType {

    ONBOARDING_START("교육이 시작되었습니다."),
    ONBOARDING_END("교육을 완료하였습니다."),
    WRITTEN_REPORT_BY_MENTEE("멘티가 보고서를 작성하였습니다.");

    ;


    private final String message;
}
