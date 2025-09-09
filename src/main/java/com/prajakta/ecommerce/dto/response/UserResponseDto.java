package com.prajakta.ecommerce.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;

    private String firstName;

    private String email;

    private String mobileNumber;


}
