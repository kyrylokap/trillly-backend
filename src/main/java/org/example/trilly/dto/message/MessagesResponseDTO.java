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
    private List<String> messages;
    private List<String> times;
    private List<String> senders;
    private List<String> types;
    private List<Long> ids;
}
