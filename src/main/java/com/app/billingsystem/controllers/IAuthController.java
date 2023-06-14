package com.app.billingsystem.controllers;

import com.app.billingsystem.models.dtos.JwtAuthResponse;
import com.app.billingsystem.models.dtos.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    /**
     * authenticate user by emailId and password
     * */
    @PostMapping("/login")
    ResponseEntity<JwtAuthResponse> authenticate(@RequestBody LoginDto loginDto);
}
