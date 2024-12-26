package spring.hi_hello_spring.group.command.domain.repository;

import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;

import java.util.List;

public interface GroupMemberRepository {

    GroupMember save(GroupMember groupMember);

    List<GroupMember> findByTaskGroupSeq(Long taskGroupSeq);
}
