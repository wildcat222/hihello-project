package spring.hi_hello_spring.group.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hi_hello_spring.group.command.domain.aggregate.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
