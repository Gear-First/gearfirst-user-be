package com.gearfirst.user_be.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String region;
    private String workType;
    private String rank;
}
