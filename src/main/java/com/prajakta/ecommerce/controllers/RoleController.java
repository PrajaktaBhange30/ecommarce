package com.prajakta.ecommerce.controllers;

import com.prajakta.ecommerce.dto.request.RoleRequestDto;
import com.prajakta.ecommerce.dto.response.RoleResponseDto;
import com.prajakta.ecommerce.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor

public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto){
        RoleResponseDto role= roleService.createRole(roleRequestDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequestDto roleRequestDto){
      RoleResponseDto role = roleService.updateRole(id, roleRequestDto);
      return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoles(@PathVariable Long id){
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
