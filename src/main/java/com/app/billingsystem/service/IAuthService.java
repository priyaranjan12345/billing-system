package com.app.billingsystem.service;
import com.app.billingsystem.models.dtos.AuthRequest;
import com.app.billingsystem.models.entities.User;

public interface IAuthService {
    String login(AuthRequest authRequest);
    public User createUser(AuthRequest authRequest) throws Exception;
}
