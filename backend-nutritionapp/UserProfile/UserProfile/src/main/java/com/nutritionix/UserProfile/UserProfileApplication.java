package com.nutritionix.UserProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class UserProfileApplication {

	public static void main(String[] args) {
		log.debug("User profile Application Started");
		SpringApplication.run(UserProfileApplication.class, args);
	}

}
