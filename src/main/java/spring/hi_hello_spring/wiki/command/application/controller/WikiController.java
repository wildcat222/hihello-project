package spring.hi_hello_spring.wiki.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.s3.FileUploadUtil;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.dto.WikiUpdateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.service.WikiService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wiki")
@RequiredArgsConstructor
@Tag(name = "Wiki API", description = "위키 API")
public class WikiController {

    private final WikiService wikiService;
    private final FileUploadUtil fileUploadUtil;

    @PostMapping(value = "/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "위키 등록 시 사진 업로드", description = "S3에 파일을 업로드하고 URL를 반환하는 로직입니다.")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestPart("file") MultipartFile file) {
        try {
            // 파일 업로드 후 S3 URL 반환 (확장자 검증도 내부에서 수행)
            String fileUrl = fileUploadUtil.uploadFile(file);
            // URL을 JSON 형태로 반환
            Map<String, String> response = new HashMap<>();
            response.put("fileUrl", fileUrl);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 확장자 검증 실패 시 처리
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "위키 등록", description = "위키 등록 로직입니다.")
    public ApiResponse<?> createWiki(
            @RequestBody WikiCreateRequestDTO wikiCreateRequestDTO
    )
    {
        wikiService.createWiki(wikiCreateRequestDTO);
        return ResponseUtil.successResponse("위키가 성공적으로 등록되었습니다.").getBody();
    }

    @PutMapping("/{wikiSeq}")
    @Operation(summary = "위키 수정", description = "위키 수정 로직입니다.")
    public ApiResponse<?> updateWiki(
            @PathVariable Long wikiSeq,
            @RequestBody WikiUpdateRequestDTO wikiUpdateRequestDTO
    ) throws IOException {
        wikiService.updateWiki(wikiSeq, wikiUpdateRequestDTO);
        return ResponseUtil.successResponse("위키가 성공적으로 수정되었습니다.").getBody();
    }

    @DeleteMapping("/{wikiSeq}")
    @Operation(summary = "위키 삭제", description = "위키 삭제 로직입니다.")
    public ApiResponse<?> deleteWiki(
            @PathVariable Long wikiSeq
    ) {
        wikiService.deleteWiki(wikiSeq);
        return ResponseUtil.successResponse("위키가 성공적으로 삭제되었습니다.").getBody();
    }
}