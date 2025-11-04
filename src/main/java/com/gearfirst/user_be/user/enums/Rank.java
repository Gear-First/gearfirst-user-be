package com.gearfirst.user_be.user.enums;

import lombok.Getter;

@Getter
public enum Rank {
    EMPLOYEE("사원"),
    LEADER("팀장"),
    // WAREHOUSE (창고)
    WAREHOUSE_MANAGER("창고 관리자"),
    // BRANCH (대리점)
    BRANCH_ENGINEER("엔지니어");

    private final String status;

    Rank(String status) {this.status = status;}
}
