package com.gearfirst.user_be.workType.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkTypeResponse {
    private Long workTypeId;
    private String workTypeName;
}
