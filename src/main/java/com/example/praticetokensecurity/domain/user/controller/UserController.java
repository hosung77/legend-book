package com.example.praticetokensecurity.domain.user.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserResponseDto>> findOne(
        @AuthenticationPrincipal CustomUserPrincipal authUser) {

        UserResponseDto response = userService.findOne(authUser);

        return ApiResponse.onSuccess(SuccessStatus.USER_GET_SUCCESS, response);
    }

    @PatchMapping("/users/update")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @RequestBody UserUpdateRequestDto updateRequestDto
    ) {

        UserResponseDto response = userService.updateUser(authUser, updateRequestDto);

        return ApiResponse.onSuccess(SuccessStatus.USER_UPDATE_SUCCESS, response);
    }

}
