package com.app.billingsystem.service;

import com.app.billingsystem.models.dtos.AuthRequest;
import com.app.billingsystem.models.entities.Role;
import com.app.billingsystem.repository.RoleRepository;
import com.app.billingsystem.repository.UserRepository;
import com.app.billingsystem.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.billingsystem.models.entities.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;

    @Override
    public String login(AuthRequest authRequest) {
        // authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtTokenProvider.generateToken(authentication);
        } else {
            return "Error: Access token denied";
        }
    }

    @Override
    public String getUsername(String token) {
        return jwtTokenProvider.getUsername(token);
    }

    @Override
    public User createUser(AuthRequest authRequest) throws Exception {
        boolean isUserExist = userRepository.existsByEmail(authRequest.getEmail());
        Optional<Role> role = roleRepository.findById(2L);
        Set<Role> roles = new HashSet<>();
        role.ifPresent(roles::add);

        // check user present or not
        if (isUserExist) {
            throw new Exception("User already exist.");
        } else {
            User user = User.builder()
                    .email(authRequest.getEmail())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .roles(roles)
                    .build();

            return userRepository.save(user);
        }
    }

    @Override
    public String logout(String token) {
        return null;
    }
}

