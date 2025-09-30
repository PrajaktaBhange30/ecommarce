package com.prajakta.ecommerce.services.Impl;

import com.prajakta.ecommerce.dto.request.RoleRequestDto;
import com.prajakta.ecommerce.dto.response.RoleResponseDto;
import com.prajakta.ecommerce.entity.Permission;
import com.prajakta.ecommerce.entity.Role;
import com.prajakta.ecommerce.exception.ConflictException;
import com.prajakta.ecommerce.exception.ResourceNotFoundException;
import com.prajakta.ecommerce.repository.PermissionRepository;
import com.prajakta.ecommerce.repository.RoleRepository;
import com.prajakta.ecommerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponseDto createRole(RoleRequestDto roleRequestDto) {
        if(roleRepository.existsByName(roleRequestDto.getName())){
            throw new ConflictException(" already role name exist.."+ roleRequestDto.getName());
        }
        Set<Permission> permissions = new HashSet<>();
        if(roleRequestDto.getPermissions() != null){
            for(String pName: roleRequestDto.getPermissions()){
                Permission permission = permissionRepository.findByName(pName)
                        .orElseThrow(()-> new ResourceNotFoundException(" Permission not found:" + pName));
                permissions.add(permission);
            }
        }
        Role role = new Role();
        role.setName(roleRequestDto.getName());
        role.setDescription(roleRequestDto.getDescription());
        role.setPermissions(permissions);
        roleRepository.save(role);

        RoleResponseDto responseDto = new RoleResponseDto();
        responseDto.setId(role.getId());
        responseDto.setName(role.getName());
        responseDto.setDescription(role.getDescription());
        responseDto.setPermissions(permissions.stream()
                .map(Permission :: getName).collect(Collectors.toSet()));
        return responseDto;
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<Role> roles = roleRepository.findAllWithPermissions();

        return roles.stream().map(role-> {
           RoleResponseDto res = new RoleResponseDto();

           res.setId(role.getId());
           res.setName(role.getName());
           res.setDescription(role.getDescription());
           res.setPermissions(role.getPermissions().stream().map(Permission :: getName).collect(Collectors.toSet()));
           return res;
        }).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto updateRole(Long id, RoleRequestDto roleRequestDto) {

        Role role= roleRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Role id not found..."));
        if(roleRequestDto.getName() != null && !role.getName().equals(roleRequestDto.getName())){
            if(roleRepository.existsByName(roleRequestDto.getName())){
                throw new ConflictException(" already role name exist.."+ roleRequestDto.getName());
            }

            role.setName(roleRequestDto.getName());
        }

        if (roleRequestDto.getDescription() != null) {
            role.setDescription(roleRequestDto.getDescription());
        }
        if (roleRequestDto.getPermissions() != null ) {
            Set<Permission> permissions = roleRequestDto.getPermissions().stream()
                    .map(permissionName -> permissionRepository.findByName(permissionName)
                            .orElseThrow(() -> new ResourceNotFoundException("Permission not Found " + permissionName)))
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }
        roleRepository.save(role);

        RoleResponseDto responseDto = new RoleResponseDto();
        responseDto.setId(role.getId());
        responseDto.setName(role.getName());
        responseDto.setDescription(role.getDescription());
        responseDto.setPermissions(role.getPermissions().stream()
                .map(Permission :: getName).collect(Collectors.toSet()));
        return responseDto;
}

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role not found..."));
        roleRepository.delete(role);

    }
}
