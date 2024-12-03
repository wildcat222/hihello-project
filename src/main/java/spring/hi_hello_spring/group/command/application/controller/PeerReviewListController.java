package spring.hi_hello_spring.group.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.group.command.application.dto.PeerReviewListCreateDTO;
import spring.hi_hello_spring.group.command.application.service.PeerReviewListService;

@RestController
@RequestMapping("api/v1/hr/peer/review/list")
@RequiredArgsConstructor
@Tag(name = "PeerReviewList API", description = "동료 평가 지표 관련 API")
public class PeerReviewListController {

    private final PeerReviewListService peerReviewListService;

    @PostMapping
    @Operation(summary = "동료 평가 지표 생성", description = "동료 평가 지표 생성 로직입니다.")
    public ApiResponse<?> createPeerReviewList(PeerReviewListCreateDTO createDTO){

        peerReviewListService.createPeerReviewList(createDTO);
        return ResponseUtil.successResponse("동료 평가 지표가 성공적으로 등록되었습니다.").getBody();
    }
}
