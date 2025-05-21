package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.responseDto.BookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentalResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.keyword.entity.Keyword;
import com.example.praticetokensecurity.domain.keyword.repository.KeywordRepository;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

    /**
     * 도서 단 건 조회
     */
    public BookResponseDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));
        return BookResponseDto.from(book);
    }

    // 캐시 o
    @Cacheable(value = "bookSearchCache", key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<BookResponseDto> searchByTitle(String keyword, Pageable pageable) {
        Page<Book> searchedBooks = bookRepository.findByTitle(keyword, pageable);
        keywordRepository.save(Keyword.of(keyword));
        return BookResponseDto.fromEntityPage(searchedBooks);
    }

    // 캐시 x
    public Page<BookResponseDto> searchByTitleWithoutCache(String keyword, Pageable pageable) {
        Page<Book> searchedBooks = bookRepository.findByTitle(keyword, pageable);
        keywordRepository.save(Keyword.of(keyword));
        return BookResponseDto.fromEntityPage(searchedBooks);
    }


    /**
     * 도서 대여
     */
    @Transactional
    public RentalResponseDto rentBook(Long bookId, String rentStatus, Long userId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));

        if (book.getBookStatus() == BookStatus.RENTED) {
            throw new ApiException(ErrorStatus.BOOK_ALREADY_RENTED);
        }
        BookStatus status = BookStatus.of(rentStatus);

        if (status != BookStatus.RENTED) {
            throw new ApiException(ErrorStatus.INVALID_BOOK_STATUS);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorStatus.USER_NOT_FOUND));

        book.updateStatus(status);
        book.assignUser(user); // 대여자 지정

        return RentalResponseDto.from(book);
    }

    /**
     * 도서 반납
     */
    @Transactional
    public RentalResponseDto returnBook(Long bookId, String rentStatus, Long userId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));

        if (!book.getUser().getId().equals(userId)) {
            throw new ApiException(ErrorStatus.BOOK_RETURN_FORBIDDEN);
        }

        if (book.getBookStatus() == BookStatus.AVAILABLE) {
            throw new ApiException(ErrorStatus.BOOK_ALREADY_AVAILABLE);
        }

        BookStatus status = BookStatus.of(rentStatus);
        if (status != BookStatus.AVAILABLE) {
            throw new ApiException(ErrorStatus.INVALID_BOOK_STATUS);
        }

        book.updateStatus(status);
        book.clearUser(); // 유저 정보 제거

        return RentalResponseDto.from(book);
    }


}

