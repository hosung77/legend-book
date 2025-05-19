package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.BookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 도서 단 건 조회
     */
    public BookResponseDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));

        return new BookResponseDto(
            book.getId(),
            book.getTitle(),
            book.getAuthorName(),
            book.getPublisher(),
            book.getBookStatus().name()
        );
    }
}

