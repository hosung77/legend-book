package com.example.praticetokensecurity.domain.user.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentedBookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.like.dto.response.LikedResponseDto;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.like.repository.LikeRepository;
import com.example.praticetokensecurity.domain.user.dto.request.UserDeleteRequestDto;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findOne(@AuthenticationPrincipal CustomUserPrincipal authUser) {
        Long userId = authUser.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorStatus.USER_NOT_FOUND));

        return new UserResponseDto(
            user.getUserName(),
            user.getEmail(),
            user.getPhoneNum(),
            user.getUserRole().name()
        );
    }

    @Transactional
    public UserResponseDto updateUser(@AuthenticationPrincipal CustomUserPrincipal authUser,
        UserUpdateRequestDto updateRequestDto) {
        Long userId = authUser.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorStatus.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(updateRequestDto.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 비밀번호를 변경할 경우
        if (updateRequestDto.getNewPassword() != null && !updateRequestDto.getNewPassword()
            .isBlank()) {
            user.updatePassword(passwordEncoder.encode(updateRequestDto.getNewPassword()));
        }

        // 이름 또는 전화번호 수정
        user.update(updateRequestDto);

        return new UserResponseDto(
            user.getUserName(),
            user.getEmail(),
            user.getPhoneNum(),
            user.getUserRole().name()
        );
    }

    public Page<RentedBookResponseDto> getMyRentBook(Long userId, Pageable pageable) {
        Page<Book> books = bookRepository.findByUserIdAndBookStatus(userId,
            BookStatus.RENTED, pageable);
        return books.map(RentedBookResponseDto::from);
    }

    @Transactional
    public void deleteUser(CustomUserPrincipal authUser, UserDeleteRequestDto requestDto) {
        User user = userRepository.findById(authUser.getUser().getId())
            .orElseThrow(() -> new ApiException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 대여 중인 도서들 반납 처리
        List<Book> rentedBooks = bookRepository.findAllByUserIdAndBookStatus(
            user.getId(), BookStatus.RENTED
        );

        for (Book book : rentedBooks) {
            book.returnBookByForce();
        }

        user.delete();
    }


    public Page<LikedResponseDto> getMyLikedBook(Long userId, Pageable pageable) {
        Page<Like> likes = likeRepository.findByUserId(userId, pageable);
        return likes.map(LikedResponseDto::from);
    }

    @Cacheable(value = "likes", key = "#userId + '_' + #pageable.pageNumber")
    public Page<LikedResponseDto> getMyLikedBookV2(Long userId, Pageable pageable) {
        Page<Like> likes = likeRepository.findByUserId(userId, pageable);
        return likes.map(LikedResponseDto::from);
    }
}
