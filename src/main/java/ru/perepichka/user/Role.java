package ru.perepichka.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum Role {
    CUSTOMER, OPERATOR, ADMIN;

    public Set<GrantedAuthority> getPermissions() {
        return Set.of(new SimpleGrantedAuthority("ROLE_"+this.name()));
    }
}
