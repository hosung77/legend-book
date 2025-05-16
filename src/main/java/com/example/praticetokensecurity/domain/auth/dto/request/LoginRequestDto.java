package com.example.praticetokensecurity.domain.auth.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @Valid
    private String email;
    @Valid
    private String password;
}
