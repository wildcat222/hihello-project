package spring.hi_hello_spring.test.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TestAllQueryDTO {

    private Long testSeq;
    private String content;
}
