package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checklist")
@NoArgsConstructor
@Getter
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checklistSeq;

    private String checklistContent;

    private Long templateSeq;
}
