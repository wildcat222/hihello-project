package spring.hi_hello_spring.mentoring.command.domain.repository;

import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Mentoring;

public interface MentoringRepository {

    Mentoring save(Mentoring mentoringGroup);
    
    Mentoring findByMentoringSeq(Long mentoringSeq);

    Mentoring findByMenteeSeq(Long menteeSeq);

    Mentoring findByChatRoomSeq(String roomId);

    Mentoring findByMentorSeqAndMentoringActiveStatusIsTrue(Long employeeSeq);
}