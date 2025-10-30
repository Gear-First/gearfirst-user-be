package com.gearfirst.user_be.user.entity;

import com.gearfirst.user_be.common.entity.BaseTimeEntity;
import com.gearfirst.user_be.region.entity.RegionEntity;
import com.gearfirst.user_be.user.enums.Rank;
import com.gearfirst.user_be.workType.entity.WorkTypeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseTimeEntity {
    @Id
    private Long id;
    private String name;
    private String phoneNum;
    private String email;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_type_id")
    private WorkTypeEntity workType;
}
