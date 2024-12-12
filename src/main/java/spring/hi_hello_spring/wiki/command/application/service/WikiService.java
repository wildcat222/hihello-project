package spring.hi_hello_spring.wiki.command.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.common.util.WikiUtil;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.application.dto.WikiUpdateRequestDTO;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiModContentRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiSnapshotRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WikiService {

    private final WikiRepository wikiRepository;
    private final WikiSnapshotRepository wikiSnapshotRepository;
    private final WikiModContentRepository wikiModContentRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final WikiUtil wikiUtil;

    @Transactional
    public void createWiki(WikiCreateRequestDTO wikiCreateRequestDTO) {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        // 위키 생성
        Wiki wiki = Wiki.builder()
                .wikiTitle(wikiCreateRequestDTO.getWikiTitle())
                .wikiCurrentVer(1)
                .build();
        wikiRepository.save(wiki);

        // 위키 snapshot 생성
        WikiSnapshot wikiSnapshot = modelMapper.map(wikiCreateRequestDTO, WikiSnapshot.class);
        wikiSnapshot.updateWikiSeq(wiki.getWikiSeq());
        wikiSnapshot.updateWikiSnapshotVer(1);
        wikiSnapshotRepository.save(wikiSnapshot);

        // 변경 내용
        String modContent = wikiUtil.processHtmlToJson(wikiCreateRequestDTO.getWikiSnapshotContent());

        // 변경 내역 기록(처음 위키 등록 시에는 전체 내용을 수정된 것으로 간주하고 저장)
        WikiModContent wikiModContent = WikiModContent.builder()
                .wikiSeq(wiki.getWikiSeq())
                .employeeSeq(1L)
                .wikiSnapshotSeq(wikiSnapshot.getWikiSnapshotSeq())
                .modContent(modContent)
                .build();
        wikiModContentRepository.save(wikiModContent);
    }

    @Transactional
    public void updateWiki(Long wikiSeq, WikiUpdateRequestDTO wikiUpdateRequestDTO) throws IOException {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        // 기존 위키 문서 가져오기
        Wiki wiki = wikiRepository.findById(wikiSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        // 현재 위키 문서의 버전
        int currentWikiVer = wiki.getWikiCurrentVer();

        // 새로 수정될 경우 생길 위키 문서의 버전
        int wikiMaxVer = wikiModContentRepository.countByWikiSeq(wikiSeq).intValue();
        // 위키 문서의 현재 버전 이전의 가장 가까운 스냅샷 버전
        int latestSnapshotVer = (currentWikiVer - 1) / 10 + 1;

        // 위키 문서의 현재 버전 이전의 가장 가까운 스냅샷
        WikiSnapshot latestWikiSnapshot = wikiSnapshotRepository.findByWikiSeqAndWikiSnapshotVer(wikiSeq, latestSnapshotVer)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        // 최신 스냅샷의 snapshotSeq 가져오기
        Long latestWikiSnapshotSeq = latestWikiSnapshot.getWikiSnapshotSeq();

        // 최신 스냅샷을 기준으로 변경된 내용들 찾기
        List<String> modContents = wikiModContentRepository.findModContentsByWikiSeqAndWikiSnapshotSeqOrderByWikiModContentSeq(wikiSeq, latestWikiSnapshotSeq);

        // 최신 스냅샷과 변경 내용을 병합해서 현재 위키 내용 가져오기
        HashMap<Integer, String> currentWikiContent = wikiUtil.mergeWikiContent(latestWikiSnapshot.getWikiSnapshotContent(), modContents);

        // 위키의 해당 스냅샷 개수 가져오기
        int numOfVersions = wikiModContentRepository.countByWikiSeqAndWikiSnapshotSeq(wikiSeq, latestWikiSnapshotSeq).intValue();

        // 만약 현재 이 위키에 존재하는 버전이 10의 배수라면, 현재의 위키는 snapshot 테이블에 저장돼야 한다.
        if (wikiMaxVer % 10 == 0) {
            String newSnapshot = wikiUpdateRequestDTO.getWikiModContent();
            WikiSnapshot newWikiSnapshot = saveSnapshot(wikiSeq, numOfVersions, newSnapshot);
            latestWikiSnapshotSeq = newWikiSnapshot.getWikiSnapshotSeq();
        }

        // 수정내용 전체를 블럭 단위로 쪼개서 modifiedContent에 저장
        HashMap<Integer, String> modifiedContents = wikiUtil.parseSnapshotJsonToHashmap(wikiUpdateRequestDTO.getWikiModContent());

        // db에 저장할 mod_content
        Map<Integer, Map<String, String>> wikiModContentHashMap = new HashMap<>();

        int maxParagraphQty = Math.max(currentWikiContent.size(), modifiedContents.size());

        for (int i = 1; i <= maxParagraphQty; i++) {
            currentWikiContent.putIfAbsent(i, ""); // currentWikiContent에 없는 인덱스는 빈 문자열로 채움
            modifiedContents.putIfAbsent(i, "");  // modifiedContents에 없는 인덱스는 빈 문자열로 채움
        }

        for (int i = 1; i <= maxParagraphQty; i++) {
            int index = i;

            if (!Objects.equals(currentWikiContent.get(i), modifiedContents.get(i))) {
                wikiModContentHashMap.put(i, new HashMap<>() {{
                    put("original", currentWikiContent.get(index));
                    put("modified", modifiedContents.get(index));
                }});
            }
        }

        String wikiModContentJson = objectMapper.writeValueAsString(wikiModContentHashMap);
        // 변경 내역 기록(처음 위키 등록 시에는 전체 내용을 수정된 것으로 간주하고 저장)
        WikiModContent wikiModContent = WikiModContent.builder()
                .wikiSeq(wiki.getWikiSeq())
                .employeeSeq(1L)
                .wikiSnapshotSeq(latestWikiSnapshotSeq)
                .modContent(wikiModContentJson)
                .build();
        wikiModContentRepository.save(wikiModContent);
        wiki.updateWikiCurrentVer(wikiMaxVer + 1);
    }

    private WikiSnapshot saveSnapshot(Long wikiSeq, int numOfVersions, String wikiModContent) {
        WikiSnapshot wikiSnapshot = WikiSnapshot.builder()
                .wikiSeq(wikiSeq)
                .wikiSnapshotVer(numOfVersions / 10 + 1)
                .wikiSnapshotContent(wikiModContent)
                .build();
        wikiSnapshotRepository.save(wikiSnapshot);
        return wikiSnapshot;
    }

    @Transactional
    public void deleteWiki(Long wikiSeq) {
        wikiRepository.deleteById(wikiSeq);
    }
}