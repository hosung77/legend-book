package com.example.praticetokensecurity.domain.book.dto;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminBookResponseDto {

    private final Long id;
    private final String title;
    private final String authorName;
    private final String publisher;
    private final BookStatus bookStatus;

    public AdminBookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthorName();
        this.publisher = book.getPublisher();
        this.bookStatus = book.getBookStatus();
    }
}
