package com.example.praticetokensecurity.domain.book.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
