package spring.hi_hello_spring.chatting.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.chatting.query.service.ChattingQueryService;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;

import java.util.List;
@RestController
@RequestMapping("api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chatting API", description = "채팅 관련 API")
public class ChattingQueryController {

    private final ChattingQueryService chattingQueryService;

    @GetMapping("/room/mentoring")
    @Operation(summary = "참여한 맨토링 채팅방 조회", description = "현재 사용자가 참여한 맨토링 채팅방을 반환합니다.")
    public ApiResponse<?> getUserMentoringChatRooms(@RequestParam Long userSeq) {
        Long roomIds = chattingQueryService.getUserMentoringChatRooms(userSeq);
        return ResponseUtil.successResponse(roomIds).getBody();
    }

    @GetMapping("/room/grouping")
    @Operation(summary = "참여한 그룹 채팅방 조회", description = "현재 사용자가 참여한 그룹 채팅방을 반환합니다.")
    public ApiResponse<List<Long>> getUserGroupingChatRooms(@RequestParam Long userSeq) {
        List<Long> roomIds = chattingQueryService.getUserGroupingChatRooms(userSeq);
        return ResponseUtil.successResponse(roomIds).getBody();
    }
}
