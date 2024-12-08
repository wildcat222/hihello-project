package spring.hi_hello_spring.group.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;
import spring.hi_hello_spring.group.command.domain.repository.GroupMemberRepository;

public interface JpaGroupMemberRepository extends GroupMemberRepository, JpaRepository<GroupMember, Long> {
}