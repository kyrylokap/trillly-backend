package org.example.trilly.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "user_chats",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;
}

