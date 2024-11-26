package spring.hi_hello_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HiHelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiHelloSpringApplication.class, args);
	}

}
