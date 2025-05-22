package com.example.praticetokensecurity.domain.auth.controller;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.config.JwtTokenProvider;
import com.example.praticetokensecurity.domain.auth.dto.request.LoginRequestDto;
import com.example.praticetokensecurity.domain.auth.dto.request.SignUpRequestDto;
import com.example.praticetokensecurity.domain.auth.dto.response.LoginResponseDto;
import com.example.praticetokensecurity.domain.auth.dto.response.SignUpResponseDto;
import com.example.praticetokensecurity.domain.auth.repository.RefreshTokenRepository;
import com.example.praticetokensecurity.domain.auth.service.AuthService;
import com.example.praticetokensecurity.domain.auth.service.RefreshTokenService;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto response = authService.signUp(signUpRequestDto.getEmail()
            , signUpRequestDto.getPassword()
            , signUpRequestDto.getUserRole()
            , signUpRequestDto.getUserName()
            , signUpRequestDto.getPhoneNum()
        );
        return ApiResponse.onSuccess(SuccessStatus.SIGNUP_SUCCESS, response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> signIn(
        @Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.signIn(requestDto.getEmail()
            , requestDto.getPassword());
        return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
        @AuthenticationPrincipal CustomUserPrincipal authUser) {

        Long userId = authUser.getId();

        refreshTokenService.deleteRefreshToken(userId);

        return ApiResponse.onSuccess(SuccessStatus.LOGOUT_SUCCESS);
    }

    @PostMapping("/token/reissue")
    public ResponseEntity<ApiResponse<LoginResponseDto>> reissue(HttpServletRequest request) {
        String refreshHeader = request.getHeader("Refresh-Token");

        // 1. 헤더 유효성 검사 및 Bearer 제거
        if (!StringUtils.hasText(refreshHeader) || !refreshHeader.startsWith("Bearer ")) {
            throw new ApiException(ErrorStatus.INVALID_TOKEN);
        }
        String refreshToken = jwtTokenProvider.subStringToken(refreshHeader);

        // 2. RefreshToken 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new ApiException(ErrorStatus.INVALID_TOKEN);
        }

        // 3. DB에 존재 여부 확인 (탈취된 토큰 방지)
        if (!refreshTokenRepository.existsByToken(refreshHeader)) {
            throw new ApiException(ErrorStatus.INVALID_TOKEN);
        }

        // 4. 이메일로 사용자 조회
        String email = jwtTokenProvider.getEmail(refreshToken);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ApiException(ErrorStatus.USER_NOT_FOUND));

        // 5. 새 토큰 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

        // 6. RefreshToken 갱신
        refreshTokenService.updateRefreshToken(user.getId(), newRefreshToken,
            LocalDateTime.now().plusDays(7));
        // 7. 응답
        LoginResponseDto responseDto = new LoginResponseDto(newAccessToken, newRefreshToken);
        return ApiResponse.onSuccess(SuccessStatus.REISSUE_SUCCESS, responseDto);
    }

}
