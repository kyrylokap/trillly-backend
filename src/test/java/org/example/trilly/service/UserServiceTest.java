package org.example.trilly.service;

import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.models.User;
import org.example.trilly.repositories.UserRepository;
import org.example.trilly.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser_whenUserDoesNotExist_shouldSaveAndReturnUser() {
        LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .username("kyrylo")
                .password("kapinos")
                .build();

        when(userRepository.existsByUsername("kyrylo")).thenReturn(false);

        User savedUser = User.builder()
                .username("kyrylo")
                .password("kapinos")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

       // var response = userService.registerUser(requestDTO);

        //assertEquals("kyrylo", response.getUsername());
        verify(userRepository).existsByUsername("kyrylo");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_whenUserExists_shouldReturnNull() {
        LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .username("kyrylo")
                .password("kapinos")
                .build();

        when(userRepository.existsByUsername("kyrylo")).thenReturn(true);

        //var response = userService.registerUser(requestDTO);

        //assertNull(response);
        verify(userRepository).existsByUsername("kyrylo");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginUser_whenUserExists_shouldReturnUser() {
        /*LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .username("kyrylo")
                .password("kapinos")
                .build();

        User foundUser = User.builder()
                .username("kyrylo")
                .password("kapinos")
                .build();

        when(userRepository.findByUsernameAndPassword("kyrylo", "kapinos")).thenReturn(foundUser);

        var response = userService.login(loginRequestDTO);

        assertEquals("kyrylo", response.getUsername());
        verify(userRepository).findByUsernameAndPassword("kyrylo", "kapinos");*/
    }

    @Test
    void testLoginUser_whenUserNotFound_shouldReturnNull() {/*
        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .username("kyrylo")
                .password("wrongpass")
                .build();

        when(userRepository.findByUsernameAndPassword("kyrylo", "wrongpass")).thenReturn(null);

        var response = userService.login(loginRequestDTO);

        assertNull(response);
        verify(userRepository).findByUsernameAndPassword("kyrylo", "wrongpass");*/
    }
}