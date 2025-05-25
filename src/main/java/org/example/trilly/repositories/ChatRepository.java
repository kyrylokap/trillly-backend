package org.example.trilly.repositories;

import org.example.trilly.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c Join c.members m where  m.id = :userId")
    List<Chat> findAllByMemberId(@Param("userId") Long userId);

    @Query("SELECT c FROM Chat c Join c.members m where  m.username = :username")
    Chat getChatByMemberUsername(@Param("username") String username);
    Chat getChatById(Long id);
}
