package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/{username}/likes")
    public ResponseEntity<List<PostResponseDTO>> getMyLikes(@PathVariable String username){
        return ResponseEntity.ok(likeService.getMyLikes(username));
    }

    @PostMapping("/{postId}/{username}")
    public ResponseEntity<Boolean> likePost(@PathVariable String username,
                                         @PathVariable Long postId){
        return ResponseEntity.ok(likeService.likePost(username, postId));
    }
}
