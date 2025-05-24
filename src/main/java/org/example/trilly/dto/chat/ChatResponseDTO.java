package org.example.trilly.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ChatResponseDTO {
    private Long chatId;
    private String lastMessage;
    private List<String> usernames;
}
