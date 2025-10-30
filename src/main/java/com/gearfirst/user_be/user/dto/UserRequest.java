package com.gearfirst.user_be.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String phoneNum;
    private Long regionId;
    private Long workTypeId;
}
