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
import com.example.praticetokensecurity.domain.auth.service.AuthService;
import com.example.praticetokensecurity.domain.token.service.RefreshTokenService;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto response = authService.signUp(signUpRequestDto.getEmail()
            , signUpRequestDto.getPassword()
            , signUpRequestDto.getUserRole()
            , signUpRequestDto.getUserName()
            , signUpRequestDto.getPhoneNum()
        );
        return ApiResponse.onSuccess(SuccessStatus.SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signIn(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.signIn(requestDto.getEmail()
            , requestDto.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token == null) {
            throw new ApiException(ErrorStatus.TOKEN_NOT_FOUND);
        }

        Claims claims = jwtTokenProvider.extractClaims(token);
        Long userId = claims.get("userId", Long.class);

        CustomUserPrincipal authUser = (CustomUserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        if (!userId.equals(authUser.getId())) {
            throw new ApiException(ErrorStatus.INVALID_TOKEN);
        }

        return ApiResponse.onSuccess(SuccessStatus.LOGOUT_SUCCESS);
    }

}
