package com.example.praticetokensecurity.common.code;

import com.example.praticetokensecurity.common.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {


    // 1000: auth
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "1101", "유저를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "1102", "유저를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "1103", "비밀번호가 일치하지 않습니다."),
    AlREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "1104", "이미 존재하는 유저입니다."),

    //2000:  User

    // 4000: Book
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "4101", "해당 도서를 찾을 수 없습니다."),
    INVALID_BOOK_STATUS(HttpStatus.BAD_REQUEST, "4102", "도서 상태 값이 유효하지 않습니다."),
    BOOK_ALREADY_RENTED(HttpStatus.BAD_REQUEST, "4103", "이미 대여중인 도서입니다."),
    BOOK_ALREADY_AVAILABLE(HttpStatus.BAD_REQUEST, "4104", "이미 반납된 도서입니다."),
    BOOK_RETURN_FORBIDDEN(HttpStatus.BAD_REQUEST, "4105", "사용자 권한이 없습니다."),

    // 5000: token
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "5101", "토큰을 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "5102", "유효하지 않은 토큰입니다.");

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
