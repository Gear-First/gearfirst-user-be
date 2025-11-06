package com.gearfirst.user_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.gearfirst.user_be.user.client")
public class UserBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserBeApplication.class, args);
	}

}
