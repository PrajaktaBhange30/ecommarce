package com.prajakta.ecommerce.security;

import com.prajakta.ecommerce.entity.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtils {
    public static Set<RoleType> extractRolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .map(String::toUpperCase)
                .filter(roleName -> {
                    try {
                        RoleType.valueOf(roleName);
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .map(RoleType::valueOf)
                .collect(Collectors.toSet());
    }
}
