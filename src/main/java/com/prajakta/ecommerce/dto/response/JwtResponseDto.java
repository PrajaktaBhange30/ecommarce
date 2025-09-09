package com.prajakta.ecommerce.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class JwtResponseDto {
    private String token;
    private String tokenType = "Bearer";
    private String email;
    private Set<String> roles;
}
