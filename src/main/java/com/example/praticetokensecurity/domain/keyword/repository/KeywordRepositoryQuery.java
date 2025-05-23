package com.example.praticetokensecurity.domain.keyword.repository;

import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import java.util.List;

public interface KeywordRepositoryQuery {
    List<Top5KeywordResponseDto> findTop5Keywords();
}
