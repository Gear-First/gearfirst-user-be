package com.gearfirst.user_be.user.client;

import com.gearfirst.user_be.user.dto.CreateAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${auth-service.url}")
public interface AuthClient {

    @PostMapping("/api/v1/auth/signup")
    String createAccount(@RequestBody CreateAccountRequest request);
}
