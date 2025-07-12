package org.example.trilly.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class NotificationResponseDTO {
    private String time;
    private String text;
    private String from;
    private String to;
}
