package org.example.trilly.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.trilly.jwt.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/isTokenExpired")
@AllArgsConstructor
@Validated
public class JWTController {
    private final JWTService jwtService;
    @GetMapping
    public ResponseEntity<?> isTokenExpired(@RequestParam @NotBlank String token){
        return ResponseEntity.ok(jwtService.isTokenExpired(token));
    }
}
