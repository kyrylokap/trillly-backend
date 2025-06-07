package org.example.trilly.dto.user.password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    private String username;
    private String prevPassword;
    private String newPassword;
}
