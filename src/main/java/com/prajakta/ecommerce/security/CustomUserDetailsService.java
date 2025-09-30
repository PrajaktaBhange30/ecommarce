package com.prajakta.ecommerce.security;


import com.prajakta.ecommerce.entity.User;
import com.prajakta.ecommerce.entity.enums.RoleType;
import com.prajakta.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private  final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found with email : " + email));



        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (RoleType role : user.getRoles()) {
            // this is role as authority you can use as :- ( ROLE_ADMIN, etc)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

            // this is permission as authority you can use as :-  ( PRODUCT_READ, USER_WRITE)
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.name()))
            );
        }


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
    }
