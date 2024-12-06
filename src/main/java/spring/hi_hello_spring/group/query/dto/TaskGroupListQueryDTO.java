package spring.hi_hello_spring.group.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class TaskGroupListQueryDTO {

    private Long taskGroupSeq;
    private String taskGroupNum;
}
