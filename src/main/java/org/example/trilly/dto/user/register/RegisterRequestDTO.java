package org.example.trilly.dto.user.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String confirmPassword;
}
