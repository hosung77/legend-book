package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
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

}
