package com.example.praticetokensecurity.domain.auth.service;

import com.example.praticetokensecurity.domain.auth.dto.response.LoginResponseDto;
import com.example.praticetokensecurity.domain.auth.dto.response.SignUpResponseDto;
import com.example.praticetokensecurity.common.exception.CustomException;
import com.example.praticetokensecurity.common.exception.ErrorCode;
import com.example.praticetokensecurity.config.JwtTokenProvider;
import com.example.praticetokensecurity.domain.token.entity.RefreshToken;
import com.example.praticetokensecurity.domain.token.service.RefreshTokenService;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.enums.UserRole;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignUpResponseDto signUp(String email, String password, String Role, String userName, String phoneNum) {

        if (userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.AlREADY_EXIST_UESR); // 임시 에러처리
        }

        UserRole userRole = UserRole.of(Role);
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.of(email,encodedPassword,userRole,userName, phoneNum);
        userRepository.save(user);

        return SignUpResponseDto.from(email, userRole);

    }

    @Transactional
    public LoginResponseDto signIn(@Valid String email, @Valid String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(password, user.getPassword())){
            new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        RefreshToken savedRefreshToken = refreshTokenService.saveToken(refreshToken, user.getId());

        return new LoginResponseDto(savedRefreshToken.getToken());
    }
}
