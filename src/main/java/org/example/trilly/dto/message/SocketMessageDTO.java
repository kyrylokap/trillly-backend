package org.example.trilly.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketMessageDTO {
    private Long chatId;
    private String text;
    private String sender;
    private Long id;
    private String type;
    private String time;
    private boolean seen;
    private boolean edited;
}

