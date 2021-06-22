package com.lab.test.task.monitorsensors.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ROLE_ADMINISTRATOR"), USER("ROLE_VIEWER");

    private final String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
