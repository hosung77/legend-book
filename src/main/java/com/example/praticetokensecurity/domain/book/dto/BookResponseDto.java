package com.example.praticetokensecurity.domain.book.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookResponseDto {

    private final Long bookId;
    private final String title;
    private final String authorName;
    private final String publisher;
    private final String category;
    private final String bookStatus;

}
