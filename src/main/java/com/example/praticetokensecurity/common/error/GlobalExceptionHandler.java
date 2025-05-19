package com.example.praticetokensecurity.common.error;

import com.example.praticetokensecurity.common.dto.ErrorReasonDto;
import com.example.praticetokensecurity.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<ErrorReasonDto>> handleCustomException(ApiException e) {
        log.error("CustomException: {}", e.getMessage(), e);
        return ApiResponse.onFailure(e.getErrorCode());
    }

}