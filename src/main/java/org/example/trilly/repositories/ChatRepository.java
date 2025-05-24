package org.example.trilly.repositories;

import org.example.trilly.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat с Join c.members m WHERE m.id = :userId")
    List<Chat> findAllByMemberId(@Param("userId") Long userId);
    Chat getChatById(Long id);

}
