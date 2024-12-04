package spring.hi_hello_spring.wiki.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.s3.FileUploadUtil;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.service.WikiService;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "위키 API", description = "위키 API")
public class WikiController {

    private final WikiService wikiService;

    @PostMapping
    @Operation(summary = "위키 등록", description = "위키 등록 로직입니다.")
    public ApiResponse<?> createWiki(
            @RequestBody WikiCreateRequestDTO wikiCreateRequestDTO
    )
    {
        wikiService.createWiki(wikiCreateRequestDTO);
        return ResponseUtil.successResponse("위키가 성공적으로 등록되었습니다.").getBody();
    }
}