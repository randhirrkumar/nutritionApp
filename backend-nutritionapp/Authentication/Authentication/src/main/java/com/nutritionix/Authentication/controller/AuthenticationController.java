package com.nutritionix.Authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nutritionix.Authentication.exception.UserCredentialsMisMatch;
import com.nutritionix.Authentication.exception.UserCredentialsNullException;
import com.nutritionix.Authentication.model.UserCredentials;
import com.nutritionix.Authentication.model.UserProfile;
import com.nutritionix.Authentication.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials userCredentials) {
        try {
        	log.info("User trying to logging in");
            validateUserCredentials(userCredentials);
            validateUserPassword(userCredentials);

            return new ResponseEntity<>(authService.generateToken(userCredentials), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user details", HttpStatus.CONFLICT);
//        	return CustomResponse.generateResponse("Login Error - Check Credentials", HttpStatus.FORBIDDEN,(Object) e.getMessage());
        }
    }

    @GetMapping("/validateToken")
    public boolean validateUser(@RequestHeader("Authorization") String token) {
    	log.info("validating token",token);
        return authService.validateToken(token);
    }

    @GetMapping("/getUsername")
    public String getUsername(@RequestHeader("Authorization") String token) {
    	log.info("Extracting username from token",token);
    	String response = authService.extractUsername(token);
    	System.out.println(response);
        return authService.extractUsername(token);
    }

    private void validateUserCredentials(UserCredentials userCredentials) {
    	log.info("Validating User Credentials is null");
        if (userCredentials.getUsername() == null || userCredentials.getPassword() == null) {
            throw new UserCredentialsNullException("Username and password cannot be null");
        }
    }

    private void validateUserPassword(UserCredentials userCredentials) {
        UserProfile userProfile = authService.findByUsername(userCredentials.getUsername());
        if (!userCredentials.getPassword().equals(userProfile.getPassword())) {
            throw new UserCredentialsMisMatch("Invalid Username or Password");
        }
    }
}



