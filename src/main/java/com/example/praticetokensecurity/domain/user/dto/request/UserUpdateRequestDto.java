package com.example.praticetokensecurity.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    private final String userName;
    private final String password;
    private final String phoneNum;
    private final String newPassword;

}
