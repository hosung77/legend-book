package com.example.praticetokensecurity.common.response;

import com.example.praticetokensecurity.common.code.BaseCode;
import com.example.praticetokensecurity.common.code.BaseErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final Boolean isSuccess;        // 성공 여부
    private final String code;              // 응답 코드
    private final String message;           // 응답 메시지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T payload;                // 실제 응답 데이터 (성공 시 포함)

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(BaseCode code, T payload) {
        ApiResponse<T> response = new ApiResponse<>(true, code.getReasonHttpStatus().getCode(),
            code.getReasonHttpStatus().getMessage(), payload);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }

    // 성공 응답 (payload 없음)
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(BaseCode code) {
        ApiResponse<T> response = new ApiResponse<>(true, code.getReasonHttpStatus().getCode(),
            code.getReasonHttpStatus().getMessage(), null);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> onFailure(BaseErrorCode code) {
        ApiResponse<T> response = new ApiResponse<>(false, code.getReasonHttpStatus().getCode(),
            code.getReasonHttpStatus().getMessage(), null);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }
}