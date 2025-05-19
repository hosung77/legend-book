package com.example.praticetokensecurity.domain.book.enums;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import java.util.Arrays;

public enum BookStatus {
    AVAILABLE,     // 대여 가능
    RENTED,        // 대여 중
    NOTAVAILABLE;  // 대여 불가능

    public static BookStatus of(String status) {
        return Arrays.stream(BookStatus.values())
            .filter(s -> s.name().equalsIgnoreCase(status))
            .findFirst()
            .orElseThrow(() -> new ApiException(ErrorStatus.BOOK_NOT_FOUND));
    }
}
