package com.prajakta.ecommerce.services;

import com.prajakta.ecommerce.dto.response.UserResponseDto;

import java.util.Set;

public interface AdminService {
    UserResponseDto updateUserRoles(Long id, Set<String> newRoleNames);


}
