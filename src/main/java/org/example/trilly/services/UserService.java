package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.dto.user.password.ChangePasswordRequestDTO;
import org.example.trilly.dto.user.password.ChangePasswordResponseDTO;
import org.example.trilly.models.User;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        return null;
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        var user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        return user == null ? null : LoginResponseDTO.builder()
                .username(user.getUsername())
                .build();
    }

    public ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO dto){
        User user = userRepository.findByUsername(dto.getUsername());
        ChangePasswordResponseDTO responseDTO;

        if(dto.getNewPassword().length() < 8){
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Password length must be greater than 8").build();
        }else if (!user.getPassword().equals(dto.getPrevPassword())){
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Bad current password").build();
        } else{
            user.setPassword(dto.getNewPassword());
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Successfully changed password!").build();
            userRepository.save(user);
        }

        return responseDTO;
    }

}
