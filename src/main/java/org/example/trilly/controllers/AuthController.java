package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.models.User;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registerUser(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );
        User user = (User)authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(userService.login(requestDTO));
    }
}
