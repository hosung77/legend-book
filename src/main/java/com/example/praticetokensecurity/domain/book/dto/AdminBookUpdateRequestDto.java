package com.example.praticetokensecurity.domain.book.dto;

import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AdminBookUpdateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "저자는 필수입니다.")
    private String authorName;

    private String publisher;

    private BookStatus bookStatus;
}
