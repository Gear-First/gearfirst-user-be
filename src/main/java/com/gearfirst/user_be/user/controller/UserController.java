package com.gearfirst.user_be.user.controller;

import com.gearfirst.user_be.common.response.ApiResponse;
import com.gearfirst.user_be.common.response.SuccessStatus;
import com.gearfirst.user_be.user.dto.UserRequest;
import com.gearfirst.user_be.user.dto.UserResponse;
import com.gearfirst.user_be.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Get User API", description = "유저 API")
public class UserController {
    private final UserService userService;

    @PostMapping("/registUser")
    public ResponseEntity<ApiResponse<Void>> registUser(@RequestBody UserRequest userRequest) {
        userService.registUser(userRequest);

        return ApiResponse.success_only(SuccessStatus.REGIST_USER_SUCCESS);
    }

    @GetMapping("getUser")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@RequestParam String email){
        UserResponse entity = userService.getUser(email);

        return ApiResponse.success(SuccessStatus.GET_USER_SUCCESS ,entity);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestParam String email){
        userService.deleteUser(email);

        return ApiResponse.success_only(SuccessStatus.DELETE_USER_SUCCESS);
    }
}
