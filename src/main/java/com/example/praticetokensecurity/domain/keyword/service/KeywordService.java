package com.example.praticetokensecurity.domain.keyword.service;

import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public List<Top5KeywordResponseDto> getFavoriteKeyword() {
        return keywordRepository.findTop5Keywords();
    }

    @Cacheable(value = "top5Keywords")
    @Transactional(readOnly = true)
    public List<Top5KeywordResponseDto> getFavoriteKeyword2() {
        return keywordRepository.findTop5Keywords();
    }

}
