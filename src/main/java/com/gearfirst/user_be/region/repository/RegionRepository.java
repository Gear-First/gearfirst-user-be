package com.gearfirst.user_be.region.repository;

import com.gearfirst.user_be.region.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
}
