package com.gearfirst.user_be.user.spec;

import com.gearfirst.user_be.user.entity.UserEntity;
import com.gearfirst.user_be.user.enums.Rank;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    // Rank 검색 조건
    private static Specification<UserEntity> byRank(Rank rank) {
        return (root, query, cb) -> cb.equal(root.get("rank"), rank);
    }

    // WorkType 검색 조건
    private static Specification<UserEntity> byWorkType(Long workTypeId) {
        return (root, query, cb) ->
                cb.equal(root.get("workType").get("id"), workTypeId);
    }

    // Region 검색 조건
    private static Specification<UserEntity> byRegion(Long regionId) {
        return (root, query, cb) ->
                cb.equal(root.get("region").get("id"), regionId);
    }

    // name, phoneNum, email OR 검색
    private static Specification<UserEntity> byKeyword(String keyword) {
        return (root, query, cb) -> {
            String likePattern = "%" + keyword + "%";
            return cb.or(
                    cb.like(root.get("name"), likePattern),
                    cb.like(root.get("phoneNum"), likePattern),
                    cb.like(root.get("email"), likePattern)
            );
        };
    }

    // 빌더 (인자 네이밍 주의: workTypeKeyword, regionId, keyword 등)
    public static Specification<UserEntity> build(Rank rank, Long workTypeId, Long regionId, String keyword) {
        Specification<UserEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (rank != null)
            spec = spec.and(byRank(rank));
        if (workTypeId != null)
            spec = spec.and(byWorkType(workTypeId));
        if (regionId != null)
            spec = spec.and(byRegion(regionId));
        if (keyword != null && !keyword.isEmpty())
            spec = spec.and(byKeyword(keyword));

        return spec;
    }
}
