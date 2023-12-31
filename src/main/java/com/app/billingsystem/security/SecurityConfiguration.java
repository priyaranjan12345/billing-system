package com.app.billingsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.app.billingsystem.models.enums.Permission.USER_CREATE;
import static com.app.billingsystem.models.enums.Permission.USER_DELETE;
import static com.app.billingsystem.models.enums.Permission.USER_READ;
import static com.app.billingsystem.models.enums.Permission.USER_UPDATE;
import static com.app.billingsystem.models.enums.Permission.ADMIN_CREATE;
import static com.app.billingsystem.models.enums.Permission.ADMIN_DELETE;
import static com.app.billingsystem.models.enums.Permission.ADMIN_READ;
import static com.app.billingsystem.models.enums.Permission.ADMIN_UPDATE;
import static com.app.billingsystem.models.enums.Permission.MANAGER_CREATE;
import static com.app.billingsystem.models.enums.Permission.MANAGER_DELETE;
import static com.app.billingsystem.models.enums.Permission.MANAGER_READ;
import static com.app.billingsystem.models.enums.Permission.MANAGER_UPDATE;
import static com.app.billingsystem.models.enums.Role.USER;
import static com.app.billingsystem.models.enums.Role.ADMIN;
import static com.app.billingsystem.models.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                // no restrictions
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                )
                                .permitAll()
                                // management endpoints : manager and admin can access
                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                // admin endpoints : only admin can access
                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                                // users endpoint: only user can access
                                .requestMatchers("/api/v1/user/**").hasRole(USER.name())
                                .requestMatchers(GET, "/api/v1/user/**").hasAuthority(USER_READ.name())
                                .requestMatchers(POST, "/api/v1/user/**").hasAuthority(USER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/user/**").hasAuthority(USER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/user/**").hasAuthority(USER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(
                        (session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                        logoutHandler -> logoutHandler
                                .logoutUrl("/api/v1/auth/logout")
                                .logoutSuccessHandler(
                                        (request, response, authentication) -> SecurityContextHolder.clearContext()
                                )
                );

        return http.build();
    }
}