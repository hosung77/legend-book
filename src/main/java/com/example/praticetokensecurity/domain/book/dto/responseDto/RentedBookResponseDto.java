package com.example.praticetokensecurity.domain.book.dto.responseDto;

import com.example.praticetokensecurity.domain.book.entity.Book;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class RentedBookResponseDto {

    private Long bookId;
    private String title;
    private String authorName;
    private String publisher;
    private LocalDateTime createdAt;
    private String bookStatus;

    public static RentedBookResponseDto from(Book book) {
        return new RentedBookResponseDto(
            book.getId(),
            book.getTitle(),
            book.getAuthorName(),
            book.getPublisher(),
            book.getCreatedAt(),
            book.getBookStatus().name()
        );
    }

    public static Page<RentedBookResponseDto> fromEntityPage(Page<Book> books) {
        return books.map(RentedBookResponseDto::from);
    }
}
