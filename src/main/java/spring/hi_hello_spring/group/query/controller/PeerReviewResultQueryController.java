package spring.hi_hello_spring.group.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultAllQueryDTO;
import spring.hi_hello_spring.group.query.dto.PeerReviewResultDetailQueryDTO;
import spring.hi_hello_spring.group.query.service.PeerReviewResultQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/{taskSeq}/group/{taskGroupSeq}")
@RequiredArgsConstructor
@Tag(name = "PeerReviewResult API", description = "동료 평가 결과 관련 API")
public class PeerReviewResultQueryController {

    private final PeerReviewResultQueryService peerReviewResultQueryService;

    /* 동료 평가 결과 리스트 조회 */
    @GetMapping("/review")
    @Operation(summary = "동료 평가 결과 리스트 조회", description = "동료 평가 결과 리스트 조회 로직입니다.")
    public ApiResponse<?> getAllPeerReviewResult(@PathVariable Long taskGroupSeq){

        List<PeerReviewResultAllQueryDTO> allQueryDTO = peerReviewResultQueryService.getAllPeerReviewResult(taskGroupSeq);
        return ResponseUtil.successResponse("동료 평과 결과 리스트가 성공적으로 조회되었습니다.", allQueryDTO).getBody();
    }

    /* 동료 평과 결과 상세 조회*/
    @GetMapping("/review/detail")
    @Operation(summary = "동료 평가 결과 상세 조회", description = "동료 평가 결과 상세 조회 로직입니다.")
    public ApiResponse<?> getPeerReviewResultDetail(@RequestParam Long employeeSeq){

        PeerReviewResultDetailQueryDTO queryDTO = peerReviewResultQueryService.getAllPeerReviewResultDetail(employeeSeq);
        System.out.println(queryDTO);
        return ResponseUtil.successResponse("동료 평과 결과 상세 조회 성공", queryDTO).getBody();
    }

}
