package spring.hi_hello_spring.notify.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotiType {

    ONBOARDING_START("온보딩 교육 과정이 시작되었습니다."),
    ONBOARDING_END("온보딩 교육 과정을 완료하였습니다."),

    ONBOARDING_COMPLETE_BY_MENTEE("멘티가 온보딩 과정을 수행하였습니다."),
    ONBOARDING_COMPLETE_BY_MENTOR("멘토가 수행한 온보딩 과정에 대해 검토하였습니다."),

    WRITTEN_PLANER_BY_MENTOR("멘토가 멘토링 계획서를 작성하였습니다."),

    ALLOW_PLANER_BY_LEADER("멘토링 계획서가 승인되었습니다."),
    REJECT_PLANER_BY_LEADER("멘토링 계획서가 반려되었습니다."),

    WRITTEN_REPORT_BY_MENTEE("멘티가 보고서를 작성하였습니다."),

    CREATE_CHATTING_ROOM("멘토링 채팅방이 개설되었습니다."),
    CREATE_GROUP_CHATTING_ROOM("그룹 과제 채팅방이 개설되었습니다."),

    ;


    private final String message;
}
