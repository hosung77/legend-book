package com.example.praticetokensecurity.domain.like.dto.response;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.like.entity.Like;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LikedResponseDto implements Serializable{

    private Long bookId;
    private String title;
    private String authorName;
    private String publisher;

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
