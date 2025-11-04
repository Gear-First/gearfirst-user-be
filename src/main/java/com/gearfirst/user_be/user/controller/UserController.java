package com.gearfirst.user_be.user.controller;

import com.gearfirst.user_be.common.response.ApiResponse;
import com.gearfirst.user_be.common.response.SuccessStatus;
import com.gearfirst.user_be.user.dto.RegistResponse;
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

    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse<RegistResponse>> registerUser(@RequestBody UserRequest userRequest) {
        RegistResponse registerUser = userService.registerUser(userRequest);

        return ApiResponse.success(SuccessStatus.REGIST_USER_SUCCESS, registerUser);
    }

    @GetMapping("getUser")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@RequestParam Long userId){
        UserResponse entity = userService.getUser(userId);

        return ApiResponse.success(SuccessStatus.GET_USER_SUCCESS ,entity);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestParam String email){
        userService.deleteUser(email);

        return ApiResponse.success_only(SuccessStatus.DELETE_USER_SUCCESS);
    }
}
