package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.post.PostDTO;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.models.Post;
import org.example.trilly.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class PostController {
    private final PostService postService;

    @GetMapping("/{username}/posts")
    public ResponseEntity<List<PostResponseDTO>> getMyPosts(@PathVariable String username){
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @PostMapping("/user/post")
    public ResponseEntity<Void> addPost(@RequestBody PostDTO post){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.addPost(username, post);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> randomPosts(){
        return ResponseEntity.ok(postService.getRandomPosts());
    }
}
