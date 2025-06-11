package org.example.trilly.repositories;

import org.example.trilly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);

    boolean existsByUsername(String username);


    List<User> searchAllByUsernameStartingWith(@Param("username") String username);
}

