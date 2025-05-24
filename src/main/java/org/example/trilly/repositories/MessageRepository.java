package org.example.trilly.repositories;

import org.example.trilly.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByChatId(Long chatId);

}
