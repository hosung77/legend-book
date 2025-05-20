package com.example.praticetokensecurity.domain.user.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
