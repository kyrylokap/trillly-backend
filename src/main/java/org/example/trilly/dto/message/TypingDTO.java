package org.example.trilly.dto.message;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class TypingDTO {
    private Long chatId;
    private String username;
    private boolean typing;
    private String token;
}

