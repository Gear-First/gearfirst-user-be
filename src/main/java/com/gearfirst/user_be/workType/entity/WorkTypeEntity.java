package com.gearfirst.user_be.workType.entity;

import com.gearfirst.user_be.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_type")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WorkTypeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workTypeName;
}
