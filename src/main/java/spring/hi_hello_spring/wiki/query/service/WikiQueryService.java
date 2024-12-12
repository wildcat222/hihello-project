package spring.hi_hello_spring.wiki.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.WikiUtil;
import spring.hi_hello_spring.wiki.query.dto.*;
import spring.hi_hello_spring.wiki.query.mapper.WikiMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WikiQueryService {

    private final WikiMapper wikiMapper;
    private final WikiUtil wikiUtil;

    // 위키 목록 조회하기
    public List<WikiListQueryDTO> getAllWikis() {
        return wikiMapper.findAllWikis();
    }

    // 위키 히스토리 목록 조회하기
    public List<WikiHistoryListQueryDTO> getWikiHistories(Long wikiSeq) { return wikiMapper.findWikiHistories(wikiSeq);}

    // 위키 조회하기
    public WikiQueryDTO getWikiByWikiSeq(Long wikiSeq) {
        WikiSnapshotQueryDTO wikiSnapshotQueryDTO = getWikiSnapshotByWikiSeq(wikiSeq);
        List<WikiModContentQueryDTO> wikiModContentQueryDTOS = getWikiModContents(wikiSeq, wikiSnapshotQueryDTO.getWikiSnapshotSeq());
        List<String> wikiModContents = new ArrayList<>();
        for (WikiModContentQueryDTO wikiModContentQueryDTO : wikiModContentQueryDTOS) {
            wikiModContents.add(wikiModContentQueryDTO.getWikiModContent());
            System.out.println("지나가용" + wikiModContentQueryDTO.getWikiModContent());
        }
        HashMap<Integer, String> wikiContentHashMap = wikiUtil.mergeWikiContent(wikiSnapshotQueryDTO.getWikiSnapshotContent(), wikiModContents);
        String wikiContent = convertWikiContentToHtml(wikiContentHashMap);
        WikiQueryDTO wikiQueryDTO = new WikiQueryDTO();
        wikiQueryDTO.setWikiTitle(wikiSnapshotQueryDTO.getWikiTitle());
        wikiQueryDTO.setWikiContent(wikiContent);
        wikiQueryDTO.setModDate(getWikiLatestModDate(wikiSeq));

        return wikiQueryDTO;
    }

    private String convertWikiContentToHtml(HashMap<Integer, String> wikiContentHashMap) {
        return wikiContentHashMap
                .entrySet()
                .stream().sorted(Map.Entry.comparingByKey())  // Key로 정렬
                .map(Map.Entry::getValue)  // value만 추출
                .collect(Collectors.joining());  // value만 합치기
    }

    // 위키 스냅샷 조회하기
    public WikiSnapshotQueryDTO getWikiSnapshotByWikiSeq(Long wikiSeq) {
        return wikiMapper.findWikiSnapshotByWikiSeq(wikiSeq);
    }

    // 위키 수정 내용 리스트 조회하기 (최신 스냅샷 이후~현재까지의 수정 내역)
    public List<WikiModContentQueryDTO> getWikiModContents(Long wikiSeq, Long wikiSnapshotSeq) {

        return wikiMapper.findWikiModContentsByWikiSeqAndWikiSnapshotSeq(wikiSeq, wikiSnapshotSeq);
    }

    public LocalDateTime getWikiLatestModDate(Long wikiSeq) {
        return wikiMapper.findWikiLatestModDateByWikiSeq(wikiSeq);
    }

}