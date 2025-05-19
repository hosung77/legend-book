package com.example.praticetokensecurity.common.code;

import com.example.praticetokensecurity.common.dto.ErrorReasonDto;

public interface BaseErrorCode {

    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
