package spring.hi_hello_spring.chatting.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import spring.hi_hello_spring.chatting.dto.ChatRoom;
import spring.hi_hello_spring.chatting.repository.ChatRoomRepository;
import spring.hi_hello_spring.chatting.serivce.ChatRoomService;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    @PostMapping("/room")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 로직입니다.")
    public ApiResponse<?> createRoom( @RequestParam Long mentoringSeq,
                                      @RequestParam Long mentorSeq,
                                      @RequestParam Long menteeSeq) {
        chatRoomService.createChatRoom(mentoringSeq, mentorSeq, menteeSeq);
        return ResponseUtil.successResponse("그룹핑을 한 후 채팅방이 생성되었습니다.").getBody();
    }

    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable Long roomId) {
//        return chatRoomRepository.findRoomById(roomId);
//    }
}