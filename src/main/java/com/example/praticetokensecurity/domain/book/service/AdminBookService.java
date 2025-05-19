package com.example.praticetokensecurity.domain.book.service;

import com.example.praticetokensecurity.domain.book.dto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.AdminBookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final BookRepository bookRepository;

    public AdminBookResponseDto createBook(AdminBookRequestDto requestDto) {
        Book book = Book.createInfo(requestDto);
        bookRepository.save(book);
        return new AdminBookResponseDto(book);
    }


}
