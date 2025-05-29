package org.example.trilly.repositories;

import org.example.trilly.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
        SELECT m FROM Message m
        WHERE m.chat.id = :chatId
        ORDER BY m.time ASC
       """)
    List<Message> getMessagesByChatId(@Param("chatId") Long chatId);


}
