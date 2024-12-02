package spring.hi_hello_spring.chatbot.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatbot_category")
@NoArgsConstructor
@Getter
public class ChatbotCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatbotCategorySeq;

    private String chatbotCategoryContent;
}
