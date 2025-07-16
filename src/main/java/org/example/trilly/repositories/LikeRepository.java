package org.example.trilly.repositories;

import org.example.trilly.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> getAllByPostId(Long id);
    List<Like> getAllByUserId(Long userId);
    boolean existsByUserUsernameAndPostId(String username, Long postId);

    Like getFirstByUserUsernameAndPostId(String username, Long postId );
}
