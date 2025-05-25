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
        User user = userRepository.save(
                User.builder().role("USER")
                        .password(request.getPassword())
                        .username(request.getUsername())
                        .createdAt(LocalDateTime.now()).build()
        );

        return LoginResponseDTO.builder().username(user.getUsername()).build();
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        return LoginResponseDTO.builder()
                .username(userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()).getUsername())
                .build();
    }

}
