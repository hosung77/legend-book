package com.example.praticetokensecurity.common.code;

import com.example.praticetokensecurity.common.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 1000: 성공 코드
    SIGNUP_SUCCESS(HttpStatus.CREATED, "1001", "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "1002", "로그인에 성공하였습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "1003", "토큰이 재발급되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "1004", "로그아웃이 완료되었습니다."),

    // 2000: 책 성공 코드
    BOOK_CREATE_SUCCESS(HttpStatus.CREATED, "2001", "책 등록이 완료되었습니다."),
    BOOK_UPDATE_SUCCESS(HttpStatus.OK, "2002", "책 정보 수정이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
            .isSuccess(true)
            .code(code)
            .message(message)
            .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
            .isSuccess(true)
            .httpStatus(httpStatus)
            .code(code)
            .message(message)
            .build();
    }
}