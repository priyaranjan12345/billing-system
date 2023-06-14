package com.app.billingsystem.service;
import com.app.billingsystem.models.dtos.LoginDto;
public interface IAuthService {
    String login(LoginDto loginDto);
}
