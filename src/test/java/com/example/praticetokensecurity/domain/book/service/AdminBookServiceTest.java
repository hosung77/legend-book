package com.example.praticetokensecurity.domain.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.requestDto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.dto.responseDto.AdminPageResponse;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith({MockitoExtension.class})
class AdminBookServiceTest {

    @InjectMocks
    private AdminBookService adminBookService;

    @Mock
    private BookRepository bookRepository;

    private Book createBook(String title, String author, String publisher) {
        return Book.createInfo(new AdminBookRequestDto(title, author, publisher));
    }

    @AfterEach
    void afterEach() {
        System.out.println("테스트 코드 성공했습니동동");
    }

    @Test
    void 책_등록_성공() {
        // given
        AdminBookRequestDto requestDto = new AdminBookRequestDto("내가만든책", "김태정", "가짜 출판사");
        Book book = createBook("내가만든책", "김태정", "가짜 출판사");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        AdminBookResponseDto result = adminBookService.createBook(requestDto);

        // then
        assertEquals("내가만든책", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }


    @Test
    void 책_수정_성공() {
        Long bookId = 1L;
        AdminBookUpdateRequestDto updateDto = new AdminBookUpdateRequestDto(
            "수정된 제목",
            "수정된 저자",
            "수정된 출판사",
            BookStatus.AVAILABLE
        );

        Book book = createBook("내가만든책", "김태정", "가짜 출판사");
        ReflectionTestUtils.setField(book, "id", bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        AdminBookResponseDto result = adminBookService.updateBook(bookId, updateDto);

        assertEquals("수정된 제목", result.getTitle());
        assertEquals("수정된 저자", result.getAuthorName());
        assertEquals("수정된 출판사", result.getPublisher());
        assertEquals(BookStatus.AVAILABLE, result.getBookStatus());

        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void 등록된_모든_책_목록_조회_성공() {
        List<Book> bookList = List.of(
            createBook("책1", "저자1", "출판사1"),
            createBook("책2", "저자2", "출판사2"),
            createBook("책3", "저자3", "출판사3")
        );

        Page<Book> bookPage = new PageImpl<>(bookList);
        when(bookRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(bookPage);

        AdminPageResponse<AdminBookResponseDto> result = adminBookService.getAllBooks(1, 3);

        assertEquals(3, result.getContent().size());
        assertEquals("책1", result.getContent().get(0).getTitle());
        verify(bookRepository, times(1)).findAllByIsDeletedFalse(any(Pageable.class));
    }


    @Test
    void 책_삭제_성공() {
        // given
        Book book = createBook("레츠기릿", "김태정", "짜가 출판사");

        when(bookRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(book));

        // when
        adminBookService.deleteBook(1L);

        // then
        verify(bookRepository).findByIdAndIsDeletedFalse(1L);
    }
}