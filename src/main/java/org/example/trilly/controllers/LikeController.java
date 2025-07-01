package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/user/likes")
    public ResponseEntity<List<PostResponseDTO>> getMyLikes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(likeService.getMyLikes(username));
    }

    @PostMapping("/{postId}/user")
    public ResponseEntity<Boolean> likePost(@PathVariable Long postId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(likeService.likePost(username, postId));
    }
}
