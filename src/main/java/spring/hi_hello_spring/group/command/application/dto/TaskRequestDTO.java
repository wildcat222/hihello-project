package spring.hi_hello_spring.group.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskRequestDTO {

    private Long taskSeq;
    private Long taskGroupSeq;
    private List<MemberDTO> members;
}