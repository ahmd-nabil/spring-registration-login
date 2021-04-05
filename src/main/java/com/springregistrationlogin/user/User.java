package com.springregistrationlogin.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean nonExpired;
    private Boolean nonLocked;
    private Boolean credentialNonExpired;
    private Boolean enabled;

    public User(String username,
                String password,
                String email,
                UserRole userRole,
                Boolean nonExpired,
                Boolean nonLocked,
                Boolean credentialNonExpired,
                Boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialNonExpired = credentialNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
