package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.responseDto.BookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;

import com.example.praticetokensecurity.domain.keyword.entity.Keyword;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final KeywordRepository keywordRepository;

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

    @Transactional
    public Page<BookResponseDto> searchByTitle(String keyword, Pageable pageable) {
        Page<Book> searchedBooks = bookRepository.findByTitle(keyword, pageable);
        keywordRepository.save(Keyword.of(keyword));
        return BookResponseDto.fromEntityPage(searchedBooks);
    }
}

