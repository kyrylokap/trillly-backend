package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.login.LoginRequestDTO;
import org.example.trilly.dto.login.LoginResponseDTO;
import org.example.trilly.models.User;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registerUser(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(userService.login(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(userService.login(requestDTO));
    }
}
