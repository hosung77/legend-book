package com.example.praticetokensecurity.domain.keyword.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import com.example.praticetokensecurity.domain.keyword.service.KeywordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/v1/keyword/favorite")
    ResponseEntity<ApiResponse<List<Top5KeywordResponseDto>>> getFavoriteKeyword() {
        List<Top5KeywordResponseDto> response = keywordService.getFavoriteKeyword();
        return ApiResponse.onSuccess(SuccessStatus.GET_FAVORITE_KEYWORD_SUCCESS, response);
    }

    @GetMapping("/v2/keyword/favorite")
    ResponseEntity<ApiResponse<List<Top5KeywordResponseDto>>> getFavoriteKeyword2() {
        List<Top5KeywordResponseDto> response = keywordService.getFavoriteKeyword2();
        return ApiResponse.onSuccess(SuccessStatus.GET_FAVORITE_KEYWORD_SUCCESS, response);
    }


}
