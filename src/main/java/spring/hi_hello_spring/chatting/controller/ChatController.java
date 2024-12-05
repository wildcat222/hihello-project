package spring.hi_hello_spring.chatting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.chatting.dto.ChatRoom;
import spring.hi_hello_spring.chatting.serivce.ChatService;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }
}