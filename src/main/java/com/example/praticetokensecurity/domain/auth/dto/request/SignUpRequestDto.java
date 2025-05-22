package com.example.praticetokensecurity.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "권한을 입력해주세요")
    private String userRole;
    @NotBlank(message = "이름을 입력해주세요")
    private String userName;
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNum;
}
