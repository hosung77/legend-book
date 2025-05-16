package com.example.praticetokensecurity.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<String> test(){
        String test = "테스트";
        return ResponseEntity.ok(test);
    }

}
