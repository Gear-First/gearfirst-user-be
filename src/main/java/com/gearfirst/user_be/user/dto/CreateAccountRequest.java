package com.gearfirst.user_be.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountRequest {
    private String email;
    private String password;
}
