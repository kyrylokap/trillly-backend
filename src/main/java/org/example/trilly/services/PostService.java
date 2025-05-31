package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.models.Post;
import org.example.trilly.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getUserPosts(Long userId){ //TODO CREATE POST DTO
        return postRepository.getAllByUserIdOrderByPostTimeDesc(userId);
    }
}
