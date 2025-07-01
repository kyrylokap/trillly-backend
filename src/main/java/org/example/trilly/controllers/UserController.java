package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.dto.user.password.ChangePasswordRequestDTO;
import org.example.trilly.dto.user.password.ChangePasswordResponseDTO;
import org.example.trilly.dto.user.profile.UserProfileDTO;
import org.example.trilly.dto.user.search.UserSearchDTO;
import org.example.trilly.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PutMapping("/user/changePassword")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        changePasswordDTO.setUsername(auth.getName());
        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }

    @PutMapping("/user/changeUsername")
    public ResponseEntity<String> changeUsername(@RequestParam String newUsername){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oldUsername = auth.getName();
        return ResponseEntity.ok(userService.changeUsername(oldUsername, newUsername));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserProfile(username));
    }


    /*
    * method searchUsers search all users starts with searchUsername and user with username "username" have not blocked
    * */
    @GetMapping("/users/{usernameToFind}")
    public ResponseEntity<List<UserSearchDTO>> searchUsers(@PathVariable String usernameToFind){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.searchAllLikeUsername(username, usernameToFind));
    }
}
