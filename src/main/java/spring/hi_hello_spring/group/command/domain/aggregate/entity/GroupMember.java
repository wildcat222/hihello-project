package spring.hi_hello_spring.group.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_member")
@NoArgsConstructor
@Getter
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupMemberSeq;

    private Long taskGroupSeq;

    private Long employeeSeq;
}
