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

    /**
     * 도서 등록
     *
     * @param requestDto 도서 정보 요청 dto
     * @return ApiResponse에 도서 정보를 dto 형태로 담음
     */
    @PostMapping("/admin/books")
    public ResponseEntity<ApiResponse<AdminBookResponseDto>> createBook(
        @Valid @RequestBody AdminBookRequestDto requestDto
    ) {
        AdminBookResponseDto responseDto = adminBookService.createBook(requestDto);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_CREATE_SUCCESS, responseDto);
    }

    /**
     * 도서 수정
     *
     * @param id         수정할 도서 id
     * @param requestDto 수정할 도서 정보 요청 dto
     * @return ApiResponse에 수정된 도서 정보를 dto 형태로 담음
     */
    @PutMapping("/admin/books/{id}")
    public ResponseEntity<ApiResponse<AdminBookResponseDto>> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody AdminBookUpdateRequestDto requestDto) {

        AdminBookResponseDto responseDto = adminBookService.updateBook(id, requestDto);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_UPDATE_SUCCESS, responseDto);
    }

    /**
     * 등록된 모든 도서 조회
     *
     * @param page 조회할 페이지 번호
     * @param size 한 페이지에 담을 데이터 개수
     * @return ApiResponse에 page response 형태로 책 정보들을 담아 반환
     */
    @GetMapping("/admin/books")
    public ResponseEntity<ApiResponse<AdminPageResponse<AdminBookResponseDto>>> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        AdminPageResponse<AdminBookResponseDto> responseDto = adminBookService.getAllBooks(page,
            size);
        return ApiResponse.onSuccess(SuccessStatus.GET_ALL_BOOKS_SUCCESS, responseDto);
    }

    /**
     * 등록된 도서 삭제
     *
     * @param id 삭제할 도서 id
     * @return ApiResponse에 삭제 성공 코드 반환
     */
    @DeleteMapping("/admin/books/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        adminBookService.deleteBook(id);
        return ApiResponse.onSuccess(SuccessStatus.BOOK_DELETE_SUCCESS);
    }

}
