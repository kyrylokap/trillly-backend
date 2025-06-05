package org.example.trilly.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trilly.models.enums.RelationStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "relations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User firstUser;

    @ManyToOne
    private User secondUser;

    @Column(name = "relation_status")
    @Enumerated(EnumType.STRING)
    private RelationStatus relationStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
