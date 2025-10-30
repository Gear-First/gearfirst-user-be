package com.gearfirst.user_be.user.dto;

import com.gearfirst.user_be.user.enums.Rank;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String phoneNum;
    private Rank rank;
    private Long regionId;
    private Long workTypeId;
}
