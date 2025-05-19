package com.example.praticetokensecurity.domain.book.dto.responseDto;

import com.example.praticetokensecurity.domain.book.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor
public class BookResponseDto {

    private Long bookId;
    private String title;
    private String authorName;
    private String publisher;
    private String bookStatus;

    public BookResponseDto(Long bookId, String title, String authorName, String publisher, String bookStatus) {
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
        this.publisher = publisher;
        this.bookStatus = "AVAILABLE";
    }

    public static BookResponseDto from(Book book) {
        return new BookResponseDto(
            book.getId(),
            book.getTitle(),
            book.getAuthorName(),
            book.getPublisher(),
            book.getBookStatus().name()
        );
    }

    public static Page<BookResponseDto> fromEntityPage(Page<Book> books) {
        return books.map(BookResponseDto::from);
    }
}
