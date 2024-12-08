package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.serivce.ChatRoomService;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringGroupDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringMatchService {

    private final MentoringRepository mentoringRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void createMentoringGroup(List<MentoringGroupDTO> mentoringGroupDTO) {
        for (MentoringGroupDTO mentoringGroupDTOs : mentoringGroupDTO) {

            // MentoringGroup 생성
            Mentoring mentoringGroup = Mentoring.builder()
                    .mentorSeq(mentoringGroupDTOs.getMentorSeq())
                    .menteeSeq(mentoringGroupDTOs.getMenteeSeq())
                    .build();

            Mentoring savedMentoringGroup = mentoringRepository.save(mentoringGroup);
            System.out.println("MentoringGroup saved with mentoringSeq: " + savedMentoringGroup.getMentoringSeq());

            // mentoringSeq를 roomId로 사용하여 ChatRoom 생성
            Long roomId = savedMentoringGroup.getMentoringSeq();
            chatRoomService.createChatRoom(roomId, mentoringGroupDTOs.getMentorSeq(), mentoringGroupDTOs.getMenteeSeq());
        }
    }
}