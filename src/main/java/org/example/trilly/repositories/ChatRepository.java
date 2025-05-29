package org.example.trilly.repositories;

import org.example.trilly.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("""
        SELECT c FROM Chat c Join c.members m where m.username = :username
        ORDER BY (
                SELECT MAX(msg.time)
                FROM Message msg
                WHERE msg.chat = c
            ) DESC
    """)
    List<Chat> findAllByMemberUsername(@Param("username") String username);

    @Query("""
            SELECT c FROM Chat c
            JOIN c.members m1
            JOIN c.members m2
            WHERE m1.username = :finder AND m2.username = :found
           
            """)
    List<Chat> getChatByFinderAndFound(@Param("finder") String finder,
                                 @Param("found") String found);
    Chat getChatById(Long id);
}
