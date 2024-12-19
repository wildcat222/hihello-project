package spring.hi_hello_spring.onboarding.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checklist")
@NoArgsConstructor
@Getter
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistSeq;

    private String checklistContent;

    private Long templateSeq;

    @Builder
    public Checklist(String checklistContent, Long templateSeq) {
        this.checklistContent = checklistContent;
        this.templateSeq = templateSeq;
    }
}
