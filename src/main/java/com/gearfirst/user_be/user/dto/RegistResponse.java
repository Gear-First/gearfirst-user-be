package com.gearfirst.user_be.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistResponse {
    private Long userId;
}
