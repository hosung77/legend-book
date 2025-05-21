package com.example.praticetokensecurity.domain.keyword.Controller;

import com.example.praticetokensecurity.domain.keyword.entity.Keyword;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dev")
public class DevKeywordController {

    private final KeywordRepository keywordRepository;


    @PostMapping("/insert-duplicate-keywords")
    public String insertDuplicatedKeywords(@RequestParam(defaultValue = "1000") int maxKeyword) {
        List<Keyword> keywords = new ArrayList<>();

        for (int i = 1; i <= maxKeyword; i++) {
            for (int j = 0; j < i; j++) {
                keywords.add(Keyword.of("검색어_" + i));
            }
        }

        keywordRepository.saveAll(keywords);
        return "총 " + keywords.size() + "개의 중복 키워드 삽입 완료";
    }

}