package spring.hi_hello_spring.chatting.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.query.mapper.ChattingMapper;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChattingQueryService {

    private final ChattingMapper chattingMapper;

    @Transactional
    public Long getUserChatRooms(Long userSeq) {
        return chattingMapper.findChatRoomsByUserSeq(userSeq);
    }
}
