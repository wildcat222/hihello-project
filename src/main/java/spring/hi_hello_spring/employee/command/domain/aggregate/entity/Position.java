package spring.hi_hello_spring.employee.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "position")
@NoArgsConstructor
@Getter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long positionSeq;
    private String positionName;
}
