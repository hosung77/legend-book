package com.example.praticetokensecurity.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    String accessToken;
    String refreshToken;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
