package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminPageResponse;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final BookRepository bookRepository;

    public AdminBookResponseDto createBook(AdminBookRequestDto requestDto) {
        Book book = Book.createInfo(requestDto);
        bookRepository.save(book);
        return new AdminBookResponseDto(book);
    }

    @Transactional
    public AdminBookResponseDto updateBook(Long id, AdminBookUpdateRequestDto requestDto) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));
        Book.updateInfo(book, requestDto);
        return new AdminBookResponseDto(book);
    }

    public AdminPageResponse<AdminBookResponseDto> getAllBooks(int page, int size) {
        //생성일 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Book> bookPage = bookRepository.findAllByIsDeletedFalse(pageable);

        return new AdminPageResponse<>(bookPage, AdminBookResponseDto::new);
    }

    //도서 목록 조회 v2(캐싱처리)
    // 사이즈 10으로 고정이기 때문에 page1이라는 캐시 데이터만 생김
    // condition page가 1일 때만 캐싱 애초에 db 쿼리가 하나인 것은 캐싱 처리를 해도 의미가 없다 ex) join이 많은 복잡한 쿼리
    @Cacheable(value = "books", key = "'page1'", condition = "#page == 1")
    public PageResponse<AdminBookResponseDto> getAllBooksV2(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<Book> bookPage = bookRepository.findAllByIsDeletedFalse(pageable);
        Page<AdminBookResponseDto> dtoPage = bookPage.map(AdminBookResponseDto::new);
        return new PageResponse<>(dtoPage);
    }


    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));
        book.delete();
    }


}
