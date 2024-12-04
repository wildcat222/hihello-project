package spring.hi_hello_spring.mentoring.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringGroupDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;
import spring.hi_hello_spring.mentoring.command.domain.repository.MentoringRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringMatchService {

    private final MentoringRepository mentoringRepository;

    @Transactional
    public void createMentoringGroup(List<MentoringGroupDTO> mentoringGroupDTO) {
        for (MentoringGroupDTO mentoringGroupDTOs : mentoringGroupDTO) {

            Mentoring mentoringGroup = Mentoring.builder()
                    .mentorSeq(mentoringGroupDTOs.getMentorSeq())
                    .menteeSeq(mentoringGroupDTOs.getMenteeSeq())
                    .build();

            mentoringRepository.save(mentoringGroup);
        }
    }
}

