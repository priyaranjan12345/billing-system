package com.app.billingsystem.controllers;

import lombok.AllArgsConstructor;
import com.app.billingsystem.models.dtos.JwtAuthResponse;
import com.app.billingsystem.models.dtos.LoginDto;
import com.app.billingsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController{
    private AuthService authService;

    @Override
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setTokenType("Bearer");

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
