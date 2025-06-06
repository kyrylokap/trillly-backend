package org.example.trilly.dto.relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RelationRequestDTO {
    private String firstUsername;
    private String secondUsername;

}
