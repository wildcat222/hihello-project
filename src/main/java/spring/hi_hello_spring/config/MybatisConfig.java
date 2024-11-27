package spring.hi_hello_spring.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "spring.hi_hello_spring", annotationClass = Mapper.class)
public class MybatisConfig {
}
