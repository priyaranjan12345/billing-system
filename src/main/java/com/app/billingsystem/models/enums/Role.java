package com.app.billingsystem.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

@RequiredArgsConstructor
public enum Role {

    // user has no permissions
    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    USER_CREATE
            )
    ),

    // admin has all permissions
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),

    // manager has limited resource
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
