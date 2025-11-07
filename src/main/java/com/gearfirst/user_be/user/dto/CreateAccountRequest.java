package com.gearfirst.user_be.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountRequest {
    private Long userId;
    private String email;
    private String personalEmail;
}
