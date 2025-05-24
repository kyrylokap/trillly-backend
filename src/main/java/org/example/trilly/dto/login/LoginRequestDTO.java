package org.example.trilly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
