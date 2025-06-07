package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.dto.user.password.ChangePasswordRequestDTO;
import org.example.trilly.dto.user.password.ChangePasswordResponseDTO;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<LoginResponseDTO> registerUser(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }


    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(userService.login(requestDTO));
    }

    @PutMapping("/user/changePassword")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@RequestBody  ChangePasswordRequestDTO changePasswordDTO){
        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }
}
