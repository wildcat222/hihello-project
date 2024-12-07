package spring.hi_hello_spring.wiki.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.wiki.query.dto.WikiHistoryListQueryDTO;
import spring.hi_hello_spring.wiki.query.dto.WikiListQueryDTO;
import spring.hi_hello_spring.wiki.query.mapper.WikiMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WikiQueryService {

    private final WikiMapper wikiMapper;

    // 위키 목록 조회하기
    public List<WikiListQueryDTO> getAllWikis() {
        return wikiMapper.findAllWikis();
    }

    public List<WikiHistoryListQueryDTO> getWikiHistories(Long wikiSeq) { return wikiMapper.findWikiHistories(wikiSeq);}
}