package com.example.praticetokensecurity.domain.book.dto.responseDto;

import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class AdminPageResponse<T> {

    private List<T> content;         // 등록된 전체 책 데이터 리스트
    private int currentPage;         // 현재 페이지
    private int totalPages;          // 전체 페이지
    private long totalElements;      // 등록된 책 전체 개수

    public <S> AdminPageResponse(Page<S> page, Function<S, T> mapper) {
        this.content = page.map(mapper).getContent();
        this.currentPage = page.getNumber() + 1;  // 첫번째 페이지 1부터 시작
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

}
