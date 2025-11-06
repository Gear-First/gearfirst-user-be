package com.gearfirst.user_be.user.dto;

import com.gearfirst.user_be.user.enums.Rank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    private Long UserId;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @Email
    @NotBlank
    private String personalEmail;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 숫자 10~11자리여야 합니다.")
    private String phoneNum;
    private Rank rank;
    private Long regionId;
    private Long workTypeId;
}
