package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.config.CustomUserDetails;
import org.example.trilly.dto.user.register.RegisterRequestDTO;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.register.RegisterResponseDTO;
import org.example.trilly.jwt.JWTService;
import org.example.trilly.models.User;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody RegisterRequestDTO requestDTO){
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = customUserDetails.getUser();
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(Map.of("username", user.getUsername(), "token", token));
    }
}
