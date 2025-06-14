package org.example.trilly.repositories;

import org.example.trilly.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> getAllByUserIdOrderByPostTimeDesc(Long id);


    @Query(value = "SELECT * FROM posts ORDER BY  random() LIMIT 10",nativeQuery = true)
    List<Post> findRandomPosts();

}
