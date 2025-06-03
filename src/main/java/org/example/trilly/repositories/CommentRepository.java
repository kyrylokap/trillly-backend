package org.example.trilly.repositories;

import org.example.trilly.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUserUsernameOrderByCommentTimeDesc(String username);
    List<Comment> findAllByPostIdOrderByCommentTimeDesc(Long postId);
}
