package com.app.billingsystem.controllers;

import com.app.billingsystem.models.entities.User;
import lombok.AllArgsConstructor;
import com.app.billingsystem.models.dtos.JwtAuthResponse;
import com.app.billingsystem.models.dtos.AuthRequest;
import com.app.billingsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController{
    private AuthService authService;

    @Override
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest){
        String token = authService.login(authRequest);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setTokenType("Bearer");

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Override
    public User createUser(AuthRequest authRequest) throws Exception {
        return authService.createUser(authRequest);
    }

    @Override
    public String home() {
        return "Welcome ";
    }

    @Override
    public String secured() {
        return "Hello, Secured!";
    }

}
