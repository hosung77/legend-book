package com.example.praticetokensecurity.domain.like.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.like.repository.LikeRepository;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.example.praticetokensecurity.domain.like.entity.Like;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void toggleLike(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ApiException(ErrorStatus.USER_NOT_FOUND)
        );

        Book book = bookRepository.findById(bookId).orElseThrow(
            () -> new ApiException(ErrorStatus.BOOK_NOT_FOUND)
        );

        Optional<Like> existing = likeRepository.findByUserAndBook(user, book);

        if(existing.isPresent()){
            Like like = existing.get();
            like.toggle();
            log.info("🆕 기존 Like 저장됨: {}", like);
        }   else {
            Like newLike = Like.of(user, book, true);
            likeRepository.save(newLike);
            log.info("🆕 새로운 Like 저장됨: {}", newLike);
        }
    }
}
