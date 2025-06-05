package org.example.trilly.dto.relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trilly.models.enums.RelationStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RelationResponseDTO{
    private String username;
    private RelationStatus status;
    private LocalDateTime createdAt;
}
