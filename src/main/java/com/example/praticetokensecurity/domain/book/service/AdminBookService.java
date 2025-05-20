package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminPageResponse;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public AdminPageResponse<AdminBookResponseDto> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Book> bookPage = bookRepository.findAllByIsDeletedFalse(pageable);

        return new AdminPageResponse<>(bookPage, AdminBookResponseDto::new);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));
        book.delete();
    }



}
