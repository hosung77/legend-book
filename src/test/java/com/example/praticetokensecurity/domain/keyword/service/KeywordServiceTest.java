package com.example.praticetokensecurity.domain.keyword.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @InjectMocks
    private KeywordService keywordService;

    @Test
    void getFavoriteKeyword2_ShouldReturnTop5Keywords() {
        List<Top5KeywordResponseDto> mockResponse = Arrays.asList(
            new Top5KeywordResponseDto("keyword1", 50L),
            new Top5KeywordResponseDto("keyword2", 40L),
            new Top5KeywordResponseDto("keyword3", 30L),
            new Top5KeywordResponseDto("keyword4", 20L),
            new Top5KeywordResponseDto("keyword5", 10L)
        );
        when(keywordRepository.findTop5Keywords()).thenReturn(mockResponse);

        List<Top5KeywordResponseDto> result = keywordService.getFavoriteKeyword2();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getKeyword()).isEqualTo("keyword1");
        assertThat(result.get(0).getCount()).isEqualTo(50L);
    }

    @Test
    void getFavoriteKeyword2_ShouldReturnEmptyListWhenNoData() {
        when(keywordRepository.findTop5Keywords()).thenReturn(List.of());

        List<Top5KeywordResponseDto> result = keywordService.getFavoriteKeyword2();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}