package com.example.praticetokensecurity.domain.like.dto.response;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikedResponseDto {

    private final Long bookId;
    private final String title;
    private final String authorName;
    private final String publisher;

    public static LikedResponseDto from(Like like) {
        Book book = like.getBook();
        return new LikedResponseDto(
            book.getId(),
            book.getTitle(),
            book.getAuthorName(),
            book.getPublisher()
        );

    }
}
