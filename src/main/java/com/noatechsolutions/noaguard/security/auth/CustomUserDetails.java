package com.noatechsolutions.noaguard.security.auth;

import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Importante: "ROLE_" debe estar antes del nombre del rol para que Spring Security lo reconozca
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puedes adaptar si manejas expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // puedes adaptar si manejas bloqueo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // puedes adaptar si manejas expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}