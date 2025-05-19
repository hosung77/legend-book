package com.example.praticetokensecurity.domain.auth.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.auth.dto.request.LoginRequestDto;
import com.example.praticetokensecurity.domain.auth.dto.request.SignUpRequestDto;
import com.example.praticetokensecurity.domain.auth.dto.response.LoginResponseDto;
import com.example.praticetokensecurity.domain.auth.dto.response.SignUpResponseDto;
import com.example.praticetokensecurity.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(
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
    ResponseEntity<LoginResponseDto> singIn(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.signIn(requestDto.getEmail()
            , requestDto.getPassword());
        return ResponseEntity.ok(response);
    }

}
