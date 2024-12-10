package spring.hi_hello_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "spring.hi_hello_spring.chatting.command.domain.repository.mongo")
public class HiHelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiHelloSpringApplication.class, args);
	}

}
