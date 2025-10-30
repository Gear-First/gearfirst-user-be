package com.gearfirst.user_be.user.enums;

import lombok.Getter;

@Getter
public enum Rank {
    EMPLOYEE("사원"),
    LEADER("팀장");

    private final String status;

    Rank(String status) {this.status = status;}
}
