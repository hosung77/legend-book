package com.example.praticetokensecurity.domain.user.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentedBookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public Page<RentedBookResponseDto> getMyRentBook(Long userId, Pageable pageable) {
        Page<Book> books = bookRepository.findByUserIdAndBookStatus(userId,
            BookStatus.RENTED, pageable);
        return books.map(RentedBookResponseDto::from);
    }
}
