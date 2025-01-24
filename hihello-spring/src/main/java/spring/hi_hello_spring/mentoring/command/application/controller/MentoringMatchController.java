package spring.hi_hello_spring.mentoring.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringGroupDTO;
import spring.hi_hello_spring.mentoring.command.application.service.MentoringMatchService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/matching")
@RequiredArgsConstructor
@Tag(name = "Matching API", description = "매칭 관련 API")
public class MentoringMatchController {

    private final MentoringMatchService mentoringMatchService;

    @PostMapping
    @Operation(summary = "멘토-멘티 그룹 생성", description = "멘토-멘티 그룹 생성 로직입니다.")
    public ApiResponse<?> createMentoringGroup(@RequestBody List<MentoringGroupDTO> mentoringGroupDTO){

        mentoringMatchService.createMentoringGroup(mentoringGroupDTO);
        return ResponseUtil.successResponse("멘토-멘티 매칭이 성공적으로 등록되었습니다.").getBody();
    }
}
