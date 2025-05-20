package com.example.praticetokensecurity.domain.book.Controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminPageResponse;
import com.example.praticetokensecurity.domain.book.service.AdminBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @PostMapping("/admin/books")
    public ResponseEntity<ApiResponse<AdminBookResponseDto>> createBook(
        @Valid @RequestBody AdminBookRequestDto requestDto
    ) {
        AdminBookResponseDto responseDto = adminBookService.createBook(requestDto);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_CREATE_SUCCESS, responseDto);
    }

    @PutMapping("/admin/books/{id}")
    public ResponseEntity<ApiResponse<AdminBookResponseDto>> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody AdminBookUpdateRequestDto requestDto) {

        AdminBookResponseDto responseDto = adminBookService.updateBook(id, requestDto);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_UPDATE_SUCCESS, responseDto);
    }

    @GetMapping("/admin/books")
    public ResponseEntity<ApiResponse<AdminPageResponse<AdminBookResponseDto>>> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        AdminPageResponse<AdminBookResponseDto> responseDto = adminBookService.getAllBooks(page,
            size);
        return ApiResponse.onSuccess(SuccessStatus.GET_ALL_BOOKS_SUCCESS, responseDto);
    }

    @DeleteMapping("/admin/books/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        adminBookService.deleteBook(id);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_DELETE_SUCCESS);
    }

}
