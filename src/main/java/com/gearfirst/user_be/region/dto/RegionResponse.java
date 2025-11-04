package com.gearfirst.user_be.region.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionResponse {
    private Long regionId;
    private String regionName;
}
