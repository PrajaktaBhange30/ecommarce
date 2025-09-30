package com.prajakta.ecommerce.services;

import com.prajakta.ecommerce.dto.request.LoginRequestDto;
import com.prajakta.ecommerce.dto.request.UserRequestDto;
import com.prajakta.ecommerce.dto.response.JwtResponseDto;
import com.prajakta.ecommerce.dto.response.UserResponseDto;

public interface AuthService {
    UserResponseDto register(UserRequestDto userResquestDto);
    JwtResponseDto login(LoginRequestDto loginRequestDto);
    JwtResponseDto adminLogin(LoginRequestDto loginRequestDto);




}
