package com.gearfirst.user_be.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private Long regionId;
    private String region;
    private Long workTypeId;
    private String workType;
    private String rank;
    private String email;
    private String phoneNum;
}
