package com.example.praticetokensecurity.domain.auth.dto.response;

import com.example.praticetokensecurity.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private String email;
    private UserRole userRole;

    public SignUpResponseDto(String email, UserRole userRole){
        this.email = email;
        this.userRole = userRole;
    }


    public static SignUpResponseDto from(String email, UserRole userRole){
        return new SignUpResponseDto(email,userRole);
    }
}
