package com.example.praticetokensecurity.domain.book.dto.responseDto;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class AdminBookResponseDto {

    private Long id;
    private String title;
    private String authorName;
    private String publisher;
    private BookStatus bookStatus;

    public AdminBookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthorName();
        this.publisher = book.getPublisher();
        this.bookStatus = book.getBookStatus();
    }
}
