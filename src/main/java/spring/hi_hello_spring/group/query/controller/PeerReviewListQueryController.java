package spring.hi_hello_spring.group.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.group.query.dto.PeerReviewListAllQueryDTO;
import spring.hi_hello_spring.group.query.service.PeerReviewListQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/peer/review/list")
@RequiredArgsConstructor
@Tag(name = "PeerReviewList API", description = "동료 평가 지표 관련 API")
public class PeerReviewListQueryController {

    private final PeerReviewListQueryService peerReviewListQueryService;

    /* 동료 평가 지표 리스트 조회 */
    @GetMapping
    @Operation(summary = "동료 평가 지표 조회", description = "동료 평가 지표 조회 로직입니다.")
    public ApiResponse<?> getPeerReviewList() {

        List<PeerReviewListAllQueryDTO> queryDTO = peerReviewListQueryService.getAllPeersReviewList();
        return ResponseUtil.successResponse("성공적으로 동료 평가 지표를 조회하였습니다.", queryDTO).getBody();
    }
}
