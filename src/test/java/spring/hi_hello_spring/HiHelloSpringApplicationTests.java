package spring.hi_hello_spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import spring.hi_hello_spring.config.ElasticsearchIndexInitializer;
import spring.hi_hello_spring.elasticsearch.command.repository.WikiDocumentRepository;

@SpringBootTest
class HiHelloSpringApplicationTests {

	@MockBean
	private ElasticsearchIndexInitializer elasticsearchIndexInitializer;

	@MockBean
	private WikiDocumentRepository wikiDocumentRepository;

	@Test
	void contextLoads() {
	}

}
