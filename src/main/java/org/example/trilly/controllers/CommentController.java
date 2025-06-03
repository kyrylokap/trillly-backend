package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.comment.CommentDTO;
import org.example.trilly.dto.comment.CommentResponseDTO;
import org.example.trilly.models.Comment;
import org.example.trilly.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/users/{username}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getUserComments(@PathVariable String username){
        return ResponseEntity.ok(commentService.getMyAllComments(username));
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<Void> commentPost(@PathVariable Long postId,
                                            @RequestBody CommentDTO commentDTO){
        commentService.addCommentToPost(postId, commentDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getPostComments(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getPostComments(postId));
    }
}
