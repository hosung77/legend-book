package com.example.praticetokensecurity.domain.like.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.like.repository.LikeRepository;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  // MockitoExtension 등록
public class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    void 좋아요_토글_성공_새로운_좋아요_생성() {
        Long userId = 1L;
        Long bookId = 1L;

        User user = new User();
        Book book = new Book();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(likeRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());

        likeService.toggleLike(userId, bookId);

        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void 좋아요_토글_성공_기존_좋아요_변경() {
        Long userId = 1L;
        Long bookId = 1L;

        User user = new User();
        Book book = new Book();
        Like like = new Like(user, book, true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(likeRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(like));

        likeService.toggleLike(userId, bookId);

        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void 좋아요_토글_실패_사용자_없음() {
        Long userId = 1L;
        Long bookId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> likeService.toggleLike(userId, bookId));
    }

    @Test
    void 좋아요_토글_실패_도서_없음() {
        Long userId = 1L;
        Long bookId = 1L;

        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> likeService.toggleLike(userId, bookId));
    }
}