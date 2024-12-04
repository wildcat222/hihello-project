package spring.hi_hello_spring.wiki.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.wiki.command.application.dto.WikiCreateRequestDTO;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.Wiki;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiModContent;
import spring.hi_hello_spring.wiki.command.domain.aggregate.entity.WikiSnapshot;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiModContentRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiRepository;
import spring.hi_hello_spring.wiki.command.domain.repository.WikiSnapshotRepository;

@Service
@RequiredArgsConstructor
public class WikiService {

    private final WikiRepository wikiRepository;
    private final WikiSnapshotRepository wikiSnapshotRepository;
    private final WikiModContentRepository wikiModContentRepository;

    private final ModelMapper modelMapper;

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

        // 변경 내역 기록(처음 위키 등록 시에는 전체 내용을 DIFF로 저장)
        WikiModContent wikiModContent = WikiModContent.builder()
                .wikiSeq(wiki.getWikiSeq())
                .employeeSeq(1L)
                .wikiSnapshotSeq(wikiSnapshot.getWikiSnapshotSeq())
                .modContent("{\"1\": \"" + wikiCreateRequestDTO.getWikiSnapshotContent() + "\"}")
                .build();
        wikiModContentRepository.save(wikiModContent);
    }
}