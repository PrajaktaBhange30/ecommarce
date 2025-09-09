package com.prajakta.ecommerce.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
    private Set<String> permissions;
}
