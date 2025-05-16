package com.example.praticetokensecurity.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    String refreshToken;

    public LoginResponseDto(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
