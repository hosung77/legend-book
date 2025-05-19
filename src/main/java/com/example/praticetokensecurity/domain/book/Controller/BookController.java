package com.example.praticetokensecurity.domain.book.Controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.book.dto.BookResponseDto;
import com.example.praticetokensecurity.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @GetMapping("/books/{bookId}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBook(
        @PathVariable Long bookId
    ) {
        BookResponseDto responseDto = bookService.getBook(bookId);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_READ_SUCCESS, responseDto);
    }
}
