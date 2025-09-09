package com.prajakta.ecommerce.services.Impl;

import com.prajakta.ecommerce.dto.request.LoginRequestDto;
import com.prajakta.ecommerce.dto.request.UserRequestDto;
import com.prajakta.ecommerce.dto.response.JwtResponseDto;
import com.prajakta.ecommerce.dto.response.UserResponseDto;
import com.prajakta.ecommerce.entity.User;
import com.prajakta.ecommerce.exception.ConflictException;
import com.prajakta.ecommerce.repository.PermissionRepository;
import com.prajakta.ecommerce.repository.RoleRepository;
import com.prajakta.ecommerce.repository.UserRepository;
import com.prajakta.ecommerce.security.JwtUtil;
import com.prajakta.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor


public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    public UserResponseDto register(UserRequestDto userResquestDto) {
        if (userRepository.existsByEmail(userResquestDto.getEmail())){
            throw  new ConflictException("already email register..");
        }
        User user = new User();
        user.setFirstName(userResquestDto.getFirstName());
        user.setLastName(userResquestDto.getLastName());
        user.setEmail(userResquestDto.getEmail());
        user.setMobileNumber(userResquestDto.getMobileNumber());
        user.setPassword(passwordEncoder.encode(userResquestDto.getPassword()));

        return null;
    }

    @Override
    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
