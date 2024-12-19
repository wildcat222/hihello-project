package spring.hi_hello_spring.onboarding.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.command.application.dto.TeamplateOrderUpdateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TemplateCreateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TemplateUpdateList;
import spring.hi_hello_spring.onboarding.command.application.service.TemplateService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/onboarding")
@RequiredArgsConstructor
@Tag(name = "Template API", description = "온보딩 스토리 보드 관련 API")
public class TemplateController {

    private final TemplateService templateService;

    /* 온보딩 스토리보드 등록 */
    @PostMapping
    @Operation(summary = "온보딩 스토리 보드 생성", description = "온보딩 스토리 보드 생성 로직입니다.")
    public ApiResponse<?> createTemplate(@RequestBody TemplateCreateDTO createDTO){

        templateService.createTemplate(createDTO);
        return ResponseUtil.successResponse("온보딩 스토리보드가 성공적으로 등록되었습니다.").getBody();
    }

    /* 온보딩 스토리 보드 삭제 */
    @DeleteMapping("/{templateSeq}")
    @Operation(summary = "온보딩 스토리 보드 삭제", description = "온보딩 스토리 보드 생성 로직입니다.")
    public ApiResponse<?> deleteTemplate(@PathVariable Long templateSeq){

        templateService.deleteTemplate(templateSeq);
        return ResponseUtil.successResponse("온보딩 스토리보드가 성공적으로 삭제되었습니다.").getBody();
    }

    /* 온보딩 스토리보드 순서 변경 */
    @PutMapping("/updateTemplateProcedure")
    @Operation(summary = "온보딩 스토리 보드 순서 변경", description = "온보딩 스토리 보드 순서 변경 로직입니다.")
    public ApiResponse<?> updateOrderTemplate(@RequestBody TemplateUpdateList templateList) {
        // TemplateList에서 templates 목록을 추출
        List<TeamplateOrderUpdateDTO> updateDTOs = templateList.getTemplates();

        // 템플릿 순서를 업데이트하는 서비스 호출
        templateService.updateOrderTemplate(updateDTOs);

        // 성공적인 응답 반환
        return ResponseUtil.successResponse("온보딩 스토리보드 순서가 성공적으로 수정되었습니다.").getBody();
    }
}
