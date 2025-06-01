package org.example.trilly.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponseDTO {
    private Long postId;
    private String description;
    private String username;
    private String likesCount;
    private LocalDateTime postTime;
    private String mediaUrl;
    private String place;
}
