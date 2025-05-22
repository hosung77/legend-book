package com.example.praticetokensecurity.domain.book.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.book.dto.requestDto.BookRentRequestDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.BookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentalResponseDto;
import com.example.praticetokensecurity.domain.book.service.BookService;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    /**
     * 도서 단건 조회
     **/
    @GetMapping("/books/{bookId}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBook(
        @PathVariable Long bookId
    ) {
        BookResponseDto responseDto = bookService.getBook(bookId);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_READ_SUCCESS, responseDto);
    }

    /**
     * 도서 키워드 검색
     **/
    @GetMapping("/v2/books")
    public ResponseEntity<ApiResponse<Page<BookResponseDto>>> searchByKeyword(
        @RequestParam(required = false) String keyword,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<BookResponseDto> response = bookService.searchByTitle(keyword, pageable);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_READ_SUCCESS, response);
    }

    // 캐시 X
    @GetMapping("/v1/books")
    public ResponseEntity<ApiResponse<Page<BookResponseDto>>> searchBooksWithoutCache(
        @RequestParam(required = false) String keyword,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<BookResponseDto> response = bookService.searchByTitleWithoutCache(keyword, pageable);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_READ_SUCCESS, response);
    }

    @PostMapping("/books/{bookId}/rent")
    public ResponseEntity<ApiResponse<RentalResponseDto>> rentBook(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PathVariable Long bookId,
        @RequestBody @Valid BookRentRequestDto request) {

        RentalResponseDto response = bookService.rentBook(bookId, request.getBookStatus(),
            authUser.getId());
        return ApiResponse.onSuccess(SuccessStatus.BOOK_RENT_SUCCESS, response);
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<ApiResponse<RentalResponseDto>> returnBook(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PathVariable Long bookId,
        @RequestBody @Valid BookRentRequestDto request) {

        RentalResponseDto responseDto = bookService.returnBook(bookId, request.getBookStatus(),
            authUser.getId());
        return ApiResponse.onSuccess(SuccessStatus.BOOK_RETURN_SUCCESS, responseDto);
    }
}
