package com.prajakta.ecommerce.controllers;

import com.prajakta.ecommerce.dto.request.LoginRequestDto;
import com.prajakta.ecommerce.dto.request.UserRequestDto;
import com.prajakta.ecommerce.dto.response.JwtResponseDto;
import com.prajakta.ecommerce.dto.response.UserResponseDto;
import com.prajakta.ecommerce.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto){
      UserResponseDto user= authService.register(userRequestDto);
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        JwtResponseDto token= authService.login(loginRequestDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
