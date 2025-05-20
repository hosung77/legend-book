package com.example.praticetokensecurity.domain.keyword.service;

import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public List<Top5KeywordResponseDto> getFavoriteKeyword() {
        return keywordRepository.Top5KeywordResponseDto();
    }
}
