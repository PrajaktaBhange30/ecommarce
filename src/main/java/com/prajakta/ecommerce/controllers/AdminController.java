package com.prajakta.ecommerce.controllers;

import com.prajakta.ecommerce.dto.response.UserResponseDto;
import com.prajakta.ecommerce.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/user/{id}/roles")
    public ResponseEntity<UserResponseDto> updateUserRoles(@PathVariable Long id, @RequestBody Set<String> newRoleNames){
        UserResponseDto response= adminService.updateUserRoles(id, newRoleNames);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }
}
