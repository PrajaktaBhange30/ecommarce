package com.prajakta.ecommerce.services.Impl;

import com.prajakta.ecommerce.dto.response.UserResponseDto;
import com.prajakta.ecommerce.entity.User;
import com.prajakta.ecommerce.entity.enums.RoleType;
import com.prajakta.ecommerce.exception.ConflictException;
import com.prajakta.ecommerce.exception.ResourceNotFoundException;
import com.prajakta.ecommerce.repository.UserRepository;
import com.prajakta.ecommerce.security.JwtUtil;
import com.prajakta.ecommerce.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UserResponseDto updateUserRoles(Long userId, Set<String> newRoleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Convert and validate new role names against enum
        Set<RoleType> newRoles = newRoleNames.stream()
                .map(name -> {
                    try {
                        return RoleType.valueOf(name.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new ConflictException("Invalid role name: " + name + ". Valid roles: " + Set.of(RoleType.values()));
                    }
                })
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(RoleType.class)));


        if (user.getRoles().contains(RoleType.ADMIN) && !newRoles.contains(RoleType.ADMIN)) {
            throw new AccessDeniedException("Cannot remove ADMIN role from a user.");
        }

        user.setRoles(newRoles);
        userRepository.save(user);

        // Return updated response
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setMobileNumber(user.getMobileNumber());
        responseDto.setRoleNames(user.getRoles().stream().map(RoleType::name).collect(Collectors.toSet()));
        return responseDto;

    }
}
