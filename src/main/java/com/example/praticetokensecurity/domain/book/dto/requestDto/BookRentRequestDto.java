package com.example.praticetokensecurity.domain.book.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookRentRequestDto {

    @NotNull(message = "도서 상태는 필수입니다.")
    private String bookStatus;
}