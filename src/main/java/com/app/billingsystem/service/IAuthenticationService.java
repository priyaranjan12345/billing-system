package com.app.billingsystem.service;

import com.app.billingsystem.models.entities.User;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
//    public AuthenticationResponse register(RegisterRequest request);
//    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public void saveUserToken(User user, String jwtToken);
    public void revokeAllUserTokens(User user);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
