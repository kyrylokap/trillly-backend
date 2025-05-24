package org.example.trilly.repositories;

import org.example.trilly.models.Chat;
import org.example.trilly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsernameAndPassword(String username, String password);
}
