package com.example.praticetokensecurity.domain.keyword.dto.response;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class Top5KeywordResponseDto {
    private String keyword;
    private Long count;

    public Top5KeywordResponseDto(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }
}
