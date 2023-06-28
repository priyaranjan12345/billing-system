package com.app.billingsystem.service;
import com.app.billingsystem.models.dtos.AuthRequest;
import com.app.billingsystem.models.entities.User;

public interface IAuthService {
    String login(AuthRequest authRequest);
    String getUsername(String token);
    User createUser(AuthRequest authRequest) throws Exception;
    String logout(String token);
}
