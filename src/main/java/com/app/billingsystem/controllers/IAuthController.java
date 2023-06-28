package com.app.billingsystem.controllers;

import com.app.billingsystem.models.dtos.JwtAuthResponse;
import com.app.billingsystem.models.dtos.AuthRequest;
import com.app.billingsystem.models.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IAuthController {
    /**
     * authenticate user by emailId and password
     * */
    @PostMapping("/login")
    ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest loginDto);

    @PostMapping("/createUser")
    User createUser(@RequestBody AuthRequest loginDto) throws Exception;
    // delete auth user account
}
