package org.example.trilly.repositories;

import org.example.trilly.models.Position;
import org.example.trilly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> getAllByUser(User user);

    List<Position> getAllByDateTimeAfterAndUser(LocalDateTime localDateTime, User user);

    Position findFirstByUserUsernameOrderByDateTimeDesc(String username);

    List<Position> findTop5ByUserUsernameOrderByDateTimeDesc(String username);
}
