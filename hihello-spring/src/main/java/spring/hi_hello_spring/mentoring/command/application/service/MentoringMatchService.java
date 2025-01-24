package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.chatting.command.application.serivce.ChatRoomService;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringGroupDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;
import spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentoringMatchService {

    private final MentoringRepository mentoringRepository;
    private final NotifyService notifyService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void createMentoringGroup(List<MentoringGroupDTO> mentoringGroupDTO) {

        Long senderSeq = CustomUserUtils.getCurrentEmployeeSeq();
        for (MentoringGroupDTO mentoringGroupDTOs : mentoringGroupDTO) {
            // MentoringGroup 생성
            Mentoring mentoringGroup = Mentoring.builder()
                    .mentorSeq(mentoringGroupDTOs.getMentorSeq())
                    .menteeSeq(mentoringGroupDTOs.getMenteeSeq())
                    .chatRoomSeq(UUID.randomUUID().toString())
                    .build();

            Mentoring savedMentoringGroup = mentoringRepository.save(mentoringGroup);
            System.out.println("MentoringGroup saved with mentoringSeq: " + savedMentoringGroup.getMentoringSeq());
            List<Long> receivers = List.of(mentoringGroup.getMentorSeq(), mentoringGroup.getMenteeSeq());
            notifyService.send(senderSeq, receivers, NotiType.ONBOARDING_START, "");

            // mentoringSeq를 roomId로 사용하여 ChatRoom 생성
            String roomId = savedMentoringGroup.getChatRoomSeq();
            chatRoomService.createMentoringChatRoom(roomId, mentoringGroupDTOs.getMentorSeq(), mentoringGroupDTOs.getMenteeSeq());
        }
    }
}