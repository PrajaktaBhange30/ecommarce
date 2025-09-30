package com.prajakta.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class RoleRequestDto {
    @NotBlank

    private String name;

    private String description;

    private Set<String> permissions;
}
