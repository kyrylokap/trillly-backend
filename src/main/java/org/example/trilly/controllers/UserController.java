package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.dto.user.password.ChangePasswordRequestDTO;
import org.example.trilly.dto.user.password.ChangePasswordResponseDTO;
import org.example.trilly.dto.user.profile.UserProfileDTO;
import org.example.trilly.dto.user.search.UserSearchDTO;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{username}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<UserSearchDTO>> searchUsers(@PathVariable String username){
        return ResponseEntity.ok(userService.searchAllLikeUsername(username));
    }
}
