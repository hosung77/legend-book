package com.example.praticetokensecurity.domain.like.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.like.service.LikeService;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/{bookId}/like")
    ResponseEntity<ApiResponse<Void>> like(@PathVariable  Long bookId,
        @AuthenticationPrincipal CustomUserPrincipal authUser){
        likeService.toggleLike(authUser.getUser().getId(), bookId);
        return ApiResponse.onSuccess(SuccessStatus.LIKE_SUCCESS);
    }

}
