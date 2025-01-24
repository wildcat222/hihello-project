package spring.hi_hello_spring.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WikiUtil {

    private final ObjectMapper objectMapper;

    // 최신 스냅샷을 기준으로 현재까지의 변경내용을 병합해서 반환하는 메소드
    public HashMap<Integer, String> mergeWikiContent(String wikiCurrentContent, List<String> modContents) {
        // 최신 스냅샷 파싱
        String parsedSnapshotHtmlToJson = processHtmlToJson(wikiCurrentContent);
        // 최신 스냅샷 json -> hashmap
        HashMap<Integer, String> currentWikiContent = parseSnapshotJsonToHashmap(parsedSnapshotHtmlToJson);

        // 수정사항을 기존 문단에 반영해서 현재의 위키 버전 내용 찾기
        for (String modContent : modContents) {
            // json 형식을 hashmap으로 변환하기
            HashMap<Integer, Map<String, String>> modifications = parseJsonToHashmap(modContent);

            // 수정 사항을 기존 문단에 반영(즉 현재 버전의 위키 전체 내용)
            currentWikiContent = applyModification(currentWikiContent, modifications);
        }

        return currentWikiContent;
    }

    public HashMap<Integer, String> parseSnapshotJsonToHashmap(String parsedSnapshotHtmlToJson) {
        Document doc = Jsoup.parse(parsedSnapshotHtmlToJson, "", Parser.htmlParser());
        HashMap<Integer, String> hashMap = new HashMap<>();
        int index = 1;
        HashMap<Integer, String> parsedSnapshotJsonToHashmap = new HashMap<>();

        for (Element element : doc.body().children()) {
            parsedSnapshotJsonToHashmap.put(index++, element.outerHtml());
        }
        return parsedSnapshotJsonToHashmap;
    }

    private HashMap<Integer, String> applyModification(HashMap<Integer, String> snapshot, HashMap<Integer, Map<String, String>> modifications) {
        if (snapshot.size() < Collections.max(modifications.keySet())) {
            for (int i = snapshot.size() + 1; i < Collections.max(modifications.keySet())+ 1; i++) {
                snapshot.put(i, "");
            }
        }

        for (HashMap.Entry<Integer, Map<String, String>> modification : modifications.entrySet()) {
            if (snapshot.containsKey(modification.getKey())) {
                snapshot.put(modification.getKey(), modification.getValue().get("modified"));
            }
        }

        return snapshot;
    }

    private HashMap<Integer, Map<String, String>> parseJsonToHashmap(String json) {
        HashMap<Integer, Map<String, String>> hashMap = new HashMap<>();

        try {
            // JSON을 Map<String, Object>로 변환 (각 항목을 Map으로 처리)
            Map<String, Object> maps = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

            // 각 항목을 처리하여 HashMap에 추가
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                Integer index = Integer.parseInt(entry.getKey()); // 키를 Integer로 변환
                Map<String, String> valueMap = new HashMap<>();

                // "original"과 "modified" 값을 추출하여 valueMap에 저장
                Map<String, String> values = (Map<String, String>) entry.getValue(); // 각 항목의 값은 또 다른 Map
                valueMap.put("original", values.get("original"));
                valueMap.put("modified", values.get("modified"));

                // index를 키로, valueMap을 값으로 넣기
                hashMap.put(index, valueMap);
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCodeType.INVALID_SERIALIZATION);
        }
        return hashMap;
    }

    public String processHtmlToJson(String wikiSnapshotContent) {
        // HTML 파싱
        Document doc = Jsoup.parse(wikiSnapshotContent, "", Parser.htmlParser());

        int index = 1;

        Map<Integer, Map<String, String>> blockMap = new HashMap<>();

        for (Element element : doc.body().children()) {
            Map<String, String> block = new HashMap<>();
            block.put("original", "");
            block.put("modified", element.outerHtml());
            blockMap.put(index++, block);
        }

        // Map을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(blockMap);
        } catch (Exception e) {
            throw new CustomException(ErrorCodeType.INVALID_SERIALIZATION);
        }
    }
}