package org.example.trilly.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessagesResponseDTO{
    private String message;
    private String time;
    private String sender;
    private String type;
    private Long id;
    private boolean seen;
    private boolean edited;
}
