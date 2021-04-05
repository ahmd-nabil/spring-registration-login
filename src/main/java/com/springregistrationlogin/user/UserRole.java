package com.springregistrationlogin.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserRole {
    ADMIN(new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE"))),
    USER(new ArrayList<>(Arrays.asList("GET")));

    private List<SimpleGrantedAuthority> authorities;

    UserRole(List<String> permissions) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        for(String permission: permissions)
            authorities.add(new SimpleGrantedAuthority(permission));
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}
