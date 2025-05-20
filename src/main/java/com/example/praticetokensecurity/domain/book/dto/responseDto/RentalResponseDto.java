package com.example.praticetokensecurity.domain.book.dto.responseDto;

import com.example.praticetokensecurity.domain.book.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RentalResponseDto {

    private final Long bookId;
    private final String title;
    private final String rentStatus;

    public static RentalResponseDto from(Book book) {
        return new RentalResponseDto(
            book.getId(),
            book.getTitle(),
            book.getBookStatus().name()
        );
    }
}

