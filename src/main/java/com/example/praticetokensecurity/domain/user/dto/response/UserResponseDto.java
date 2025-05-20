package com.example.praticetokensecurity.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private final String userName;
    private final String email;
    private final String phoneNum;
    private final String userRole;

}
