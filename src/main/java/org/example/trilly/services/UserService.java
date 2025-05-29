package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.login.LoginRequestDTO;
import org.example.trilly.dto.login.LoginResponseDTO;
import org.example.trilly.models.User;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public LoginResponseDTO registerUser(LoginRequestDTO request){
        if(!userRepository.existsByUsername(request.getUsername())){
            var user = User.builder().role("USER")
                    .password(request.getPassword())
                    .username(request.getUsername())
                    .createdAt(LocalDateTime.now()).build();
            userRepository.save(user);
            return LoginResponseDTO.builder().username(user.getUsername()).build();
        }
        return LoginResponseDTO.builder().username("no").build();
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        var user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        return LoginResponseDTO.builder()
                .username(user == null ? "no" : user.getUsername())
                .build();
    }

}
