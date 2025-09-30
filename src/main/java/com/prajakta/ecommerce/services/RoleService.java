package com.prajakta.ecommerce.services;

import com.prajakta.ecommerce.dto.request.RoleRequestDto;
import com.prajakta.ecommerce.dto.response.RoleResponseDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto roleRequestDto);
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto updateRole(Long id, RoleRequestDto roleRequestDto);
    void deleteRole(Long id);


}
