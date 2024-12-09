package spring.hi_hello_spring.mentoring.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.s3.FileUploadUtil;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringPlanRequestDTO;
import spring.hi_hello_spring.mentoring.command.application.service.MentoringPlanService;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/mentor")
@RequiredArgsConstructor
@Tag(name = "Mentoring Plan API", description = "멘토링 계획서 관련 API")
public class MentoringPlanController {

    private final MentoringPlanService mentoringPlanService;
    private final FileUploadUtil fileUploadUtil;

    @PostMapping(value ="/plan",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멘토링 계획서 등록", description = "멘토링 계획서 등록 로직입니다.")
    public ApiResponse<?> createMentoringPlan(@RequestPart("createProductReqDTO") MentoringPlanRequestDTO mentoringPlanRequestDTO,
                                              @RequestPart("productImgUrl") MultipartFile fileUrl){

        try {
            String uploadFile = fileUploadUtil.uploadFile(fileUrl);
            mentoringPlanService.createMentoringPlan(mentoringPlanRequestDTO, uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
        return ResponseUtil.successResponse("멘토링 계획서가 성공적으로 등록 되었습니다.").getBody();
    }

}
