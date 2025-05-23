package com.example.praticetokensecurity.domain.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private KeywordRepository keywordRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void 도서_단건조회_성공() {
        // given
        Long bookId = 1L;
        Book mockBook = new Book("test", "test", "test", BookStatus.AVAILABLE);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // when
        BookResponseDto result = bookService.getBook(bookId);

        // then
        assertNotNull(result);
        assertEquals("test", result.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void 도서_단건조회_실패_도서없음() {
        Long bookId = 76L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ApiException ex = assertThrows(ApiException.class, () -> bookService.getBook(bookId));
        assertEquals(ErrorStatus.BOOK_NOT_FOUND.getMessage(), ex.getMessage());
    }

    @Test
    void 도서_대여_성공() {
        Long bookId = 1L;
        Long userId = 2L;
        Book book = new Book("test", "test", "test", BookStatus.AVAILABLE);
        User user = mock(User.class);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        RentalResponseDto result = bookService.rentBook(bookId, "RENTED", userId);

        assertEquals(BookStatus.RENTED.name(), result.getRentStatus());
        verify(bookRepository).findById(bookId);
        verify(userRepository).findById(userId);
    }

    @Test
    void 도서_대여_실패_이미대여됨() {
        Long bookId = 1L;
        Long userId = 2L;
        Book book = new Book("test", "test", "test", BookStatus.RENTED);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        ApiException ex = assertThrows(ApiException.class, () ->
            bookService.rentBook(bookId, "RENTED", userId));

        assertEquals(ErrorStatus.BOOK_ALREADY_RENTED.getMessage(), ex.getMessage());
    }

    @Test
    void 도서_반납_성공() {
        Long bookId = 1L;
        Long userId = 2L;
        User user = mock(User.class);
        Book book = new Book("test", "test", "test", BookStatus.RENTED);
        book.assignUser(user);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(user.getId()).thenReturn(userId);

        RentalResponseDto result = bookService.returnBook(bookId, "AVAILABLE", userId);

        assertEquals(BookStatus.AVAILABLE.name(), result.getRentStatus());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void 도서_반납_실패_본인아님() {
        Long bookId = 1L;
        Long userId = 99L;
        User bookUser = mock(User.class);
        Book book = new Book("test", "test", "test", BookStatus.RENTED);
        book.assignUser(bookUser);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookUser.getId()).thenReturn(123L); // 다른 사용자

        ApiException ex = assertThrows(ApiException.class, () ->
            bookService.returnBook(bookId, "AVAILABLE", userId));

        assertEquals(ErrorStatus.BOOK_RETURN_FORBIDDEN.getMessage(), ex.getMessage());
    }

    @Test
    void 도서_반납_실패_이미반납됨() {
        Long bookId = 1L;
        Long userId = 2L;
        User user = mock(User.class);
        Book book = new Book("test", "test", "test", BookStatus.AVAILABLE);
        book.assignUser(user);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(user.getId()).thenReturn(userId);

        ApiException ex = assertThrows(ApiException.class, () ->
            bookService.returnBook(bookId, "AVAILABLE", userId));

        assertEquals(ErrorStatus.BOOK_ALREADY_AVAILABLE.getMessage(), ex.getMessage());
    }

    @Test
    void 캐시적용_도서_키워드_검색_성공() {
        // given
        String keyword = "test";
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book("test", "test", "test", BookStatus.AVAILABLE);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));

        when(bookRepository.findByTitle(keyword, pageable)).thenReturn(bookPage);

        // when
        Page<BookResponseDto> result = bookService.searchByTitle(keyword, pageable);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals("test", result.getContent().get(0).getTitle());

        verify(bookRepository, times(1)).findByTitle(keyword, pageable);
        verify(keywordRepository, times(1)).save(any(Keyword.class));
    }
}
