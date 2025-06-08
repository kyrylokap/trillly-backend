package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.comment.CommentDTO;
import org.example.trilly.dto.comment.CommentResponseDTO;
import org.example.trilly.models.Comment;
import org.example.trilly.repositories.CommentRepository;
import org.example.trilly.repositories.PostRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public List<CommentResponseDTO> getMyAllComments(String username){        //TODO CHANGE ON commentDTO
        return mapCommentsToCommentsDTO(
                commentRepository.findAllByUserUsernameOrderByCommentTimeDesc(username)
        );
    }



    public List<CommentResponseDTO> getPostComments(Long postId){
        return mapCommentsToCommentsDTO(
                commentRepository.findAllByPostIdOrderByCommentTimeDesc(postId)
        );
    }

    public void addCommentToPost(Long postId, CommentDTO commentDTO){
        commentRepository.save(Comment.builder()
                .post(postRepository.findById(postId).get())
                .user(userRepository.findByUsername(commentDTO.getUsername()))
                .text(commentDTO.getText())
                .commentTime(LocalDateTime.now())
                .build());
    }
    private List<CommentResponseDTO> mapCommentsToCommentsDTO(List<Comment> comments){
        List<CommentResponseDTO> response = new ArrayList<>();
        comments.forEach(x -> {
            response.add(mapCommentToDTO(x));
        });
        return response;
    }

    private CommentResponseDTO mapCommentToDTO(Comment comment){
        return CommentResponseDTO.builder()
                .username(comment.getUser().getUsername())
                .text(comment.getText())
                .commentTime(comment.getCommentTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy  hh:mm")))
                .build();
    }

    //TODO ADD DELETE COMMENT
}
