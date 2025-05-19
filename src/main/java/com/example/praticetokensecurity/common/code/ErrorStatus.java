package com.example.praticetokensecurity.common.code;

import com.example.praticetokensecurity.common.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "유저를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "유저를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "Not Match", "비밀번호가 일치하지 않습니다."),
    AlREADY_EXIST_UESR(HttpStatus.BAD_REQUEST, "Already exist", "이미 존재하는 유저입니다."),

    // 책
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "등록되지 않은 책입니다."),

    // 토큰
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "토큰을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
            .isSuccess(false)
            .code(code)
            .message(message)
            .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
            .isSuccess(false)
            .httpStatus(httpStatus)
            .code(code)
            .message(message)
            .build();
    }
}
