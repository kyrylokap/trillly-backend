package org.example.trilly.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PostDTO{
    private String mediaUrl;
    private String description;
    private String place;
}
