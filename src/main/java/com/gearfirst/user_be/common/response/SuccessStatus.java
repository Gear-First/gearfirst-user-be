package com.gearfirst.user_be.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {
    /** 200 SUCCESS */
    REGIST_USER_SUCCESS(HttpStatus.OK, "사용자 등록 성공"),
    GET_USER_SUCCESS(HttpStatus.OK, "사용자 조회 성공"),
    DELETE_USER_SUCCESS(HttpStatus.OK, "사용자 삭제 성공"),

    /** 201 CREATED */
    CREATE_SAMPLE_SUCCESS(HttpStatus.CREATED, "샘플 등록 성공"),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
