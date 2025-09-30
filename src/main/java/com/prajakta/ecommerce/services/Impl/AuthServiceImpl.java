package com.prajakta.ecommerce.services.Impl;

import com.prajakta.ecommerce.dto.request.LoginRequestDto;
import com.prajakta.ecommerce.dto.request.UserRequestDto;
import com.prajakta.ecommerce.dto.response.JwtResponseDto;
import com.prajakta.ecommerce.dto.response.UserResponseDto;
import com.prajakta.ecommerce.entity.Role;
import com.prajakta.ecommerce.entity.User;
import com.prajakta.ecommerce.exception.ConflictException;
import com.prajakta.ecommerce.exception.ResourceNotFoundException;
import com.prajakta.ecommerce.repository.PermissionRepository;
import com.prajakta.ecommerce.repository.RoleRepository;
import com.prajakta.ecommerce.repository.UserRepository;
import com.prajakta.ecommerce.security.JwtUtil;
import com.prajakta.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

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
            throw  new ConflictException("already email register...");
        }
        if(userRepository.existsByMobileNumber(userResquestDto.getMobileNumber())){
            throw new ConflictException("mobile number already exist...");
        }
        User user = new User();
        user.setFirstName(userResquestDto.getFirstName());
        user.setLastName(userResquestDto.getLastName());
        user.setEmail(userResquestDto.getEmail());
        user.setMobileNumber(userResquestDto.getMobileNumber());
        user.setPassword(passwordEncoder.encode(userResquestDto.getPassword()));

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(()-> new ResourceNotFoundException("Default role not found"));
        user.setRoles(Set.of(customerRole));
        userRepository.save(user);

        UserResponseDto responseDto= new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setMobileNumber(user.getMobileNumber());
        responseDto.setRoleNames(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return responseDto;
    }

    @Override
    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Set<String> roles= userDetails.getAuthorities().stream()
                .map(auth-> auth.getAuthority().replace("ROLE_",""))
                .collect(Collectors.toSet());



        if(!roles.contains("CUSTOMER")){
            throw new AccessDeniedException("you have no permission to access...");
        }

        String token = jwtUtil.generateToken(userDetails.getUsername(),roles);


        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setToken(token);
        responseDto.setEmail(userDetails.getUsername());
        responseDto.setRoles(roles);

        return responseDto;
    }

    @Override
    public JwtResponseDto adminLogin(LoginRequestDto loginRequestDto) {

        Authentication authentication= authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth-> auth.getAuthority().equals("ROLE_ADMIN"));

            if(!isAdmin){
                throw new AccessDeniedException("Only admin login access here...");
            }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Set<String> roles= userDetails.getAuthorities().stream()
                .map(auth-> auth.getAuthority().replace("ROLE_",""))
                .collect(Collectors.toSet());



        if(!roles.contains("ADMIN")){
            throw new AccessDeniedException("you have no permission to access...");
        }

        String token = jwtUtil.generateToken(userDetails.getUsername(),roles);


        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setToken(token);
        responseDto.setEmail(userDetails.getUsername());
        responseDto.setRoles(roles);

        return responseDto;
    }

}
