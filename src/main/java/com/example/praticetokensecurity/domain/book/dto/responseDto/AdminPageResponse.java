package com.example.praticetokensecurity.domain.book.dto.responseDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminPageResponse<T> {

    private List<T> content;         // 등록된 전체 책 데이터 리스트
    private int currentPage;         // 현재 페이지
    private int totalPages;          // 전체 페이지
    private long totalElements;      // 등록된 책 전체 개수
}
