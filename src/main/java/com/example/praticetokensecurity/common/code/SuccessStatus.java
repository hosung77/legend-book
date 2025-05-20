package com.example.praticetokensecurity.common.code;

import com.example.praticetokensecurity.common.dto.ReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 1000: auth 성공 코드
    SIGNUP_SUCCESS(HttpStatus.CREATED, "1001", "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "1002", "로그인에 성공하였습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "1003", "토큰이 재발급되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "1004", "로그아웃이 완료되었습니다."),

    //2000: User 성공 코드
    USER_GET_SUCCESS(HttpStatus.OK, "2001", "유저 조회 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "2002", "유저 삭제 성공"),
    USER_BOOK_LIST_SUCCESS(HttpStatus.OK, "2003", "대여한 도서 목록 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "2004", "유저 수정 성공"),
    USER_LIKED_LIST_SUCCESS(HttpStatus.OK, "2005", "좋아요한 도서 목록 조회 성공"),


    // 3000: like 성공 코드
    LIKE_SUCCESS(HttpStatus.OK, "3001", "좋아요 상태가 변경되었습니다."),

    //4000: Book 성공 코드
    BOOK_READ_SUCCESS(HttpStatus.OK, "4001", "도서 조회 성공"),
    BOOK_RENT_SUCCESS(HttpStatus.OK, "4002", "도서가 대여되었습니다."),
    BOOK_RETURN_SUCCESS(HttpStatus.OK, "4003", "도서가 반납되었습니다."),

    // 6000 keyword
    GET_FAVORITE_KEYWORD_SUCCESS(HttpStatus.OK, "5001", "키워드 조회가 왼료되었습니다."),

    // 7000: ADMIN 성공 코드
    BOOK_CREATE_SUCCESS(HttpStatus.CREATED, "7001", "책 등록이 완료되었습니다."),
    BOOK_UPDATE_SUCCESS(HttpStatus.OK, "7002", "책 정보 수정이 완료되었습니다."),
    GET_ALL_BOOKS_SUCCESS(HttpStatus.OK, "7003", "등록된 모든 책이 조회되었습니다."),
    BOOK_DELETE_SUCCESS(HttpStatus.OK, "7004", "등록된 책이 삭제되었습니다.");


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