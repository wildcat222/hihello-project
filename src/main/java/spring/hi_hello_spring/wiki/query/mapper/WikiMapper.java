package spring.hi_hello_spring.wiki.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.wiki.query.dto.WikiListQueryDTO;

import java.util.List;

@Mapper
public interface WikiMapper {

    List<WikiListQueryDTO> findAllWikis();
}