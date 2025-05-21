package com.example.praticetokensecurity.domain.user.controller;

import com.example.praticetokensecurity.common.code.SuccessStatus;
import com.example.praticetokensecurity.common.response.ApiResponse;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentedBookResponseDto;
import com.example.praticetokensecurity.domain.like.dto.response.LikedResponseDto;
import com.example.praticetokensecurity.domain.user.dto.request.UserDeleteRequestDto;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

    @GetMapping("/users/rent")
    public ResponseEntity<ApiResponse<Page<RentedBookResponseDto>>> getMyRentBook(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<RentedBookResponseDto> myRentBook = userService.getMyRentBook(authUser.getId(),
            pageable);
        return ApiResponse.onSuccess(SuccessStatus.USER_BOOK_LIST_SUCCESS, myRentBook);
    }

    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse<Void>> deleteMyAccount(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @RequestBody @Valid UserDeleteRequestDto requestDto
    ) {
        userService.deleteUser(authUser, requestDto);
        return ApiResponse.onSuccess(SuccessStatus.USER_DELETE_SUCCESS, null);
    }


    @GetMapping("/users/likes")
    public ResponseEntity<ApiResponse<Page<LikedResponseDto>>> getMyLikedBook(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<LikedResponseDto> myLikedBook = userService.getMyLikedBook(authUser.getUser().getId(),
            pageable);

        return ApiResponse.onSuccess(SuccessStatus.USER_LIKED_LIST_SUCCESS, myLikedBook);
    }

    @GetMapping("/v2/users/likes")
    public ResponseEntity<ApiResponse<Page<LikedResponseDto>>> getMyLikedBookV2(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<LikedResponseDto> myLikedBook = userService.getMyLikedBookV2(
            authUser.getUser().getId(),
            pageable);

        return ApiResponse.onSuccess(SuccessStatus.USER_LIKED_LIST_SUCCESS, myLikedBook);
    }

}
