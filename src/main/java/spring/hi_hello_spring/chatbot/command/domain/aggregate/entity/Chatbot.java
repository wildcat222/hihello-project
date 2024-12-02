package spring.hi_hello_spring.chatbot.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatbot")
@NoArgsConstructor
@Getter
public class Chatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatbotSeq;

    private Long chatbotCategorySeq;

    private String chatbotData;

}
