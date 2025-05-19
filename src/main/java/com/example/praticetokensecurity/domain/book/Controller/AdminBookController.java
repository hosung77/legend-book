package com.example.praticetokensecurity.domain.book.Controller;

import com.example.praticetokensecurity.domain.book.dto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.service.AdminBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @PostMapping("/admin/books")
    public ResponseEntity<AdminBookResponseDto> createBook(
        @Valid @RequestBody AdminBookRequestDto requestDto) {

        AdminBookResponseDto responseDto = adminBookService.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
