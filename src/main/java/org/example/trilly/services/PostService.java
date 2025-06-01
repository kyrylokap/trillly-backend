package org.example.trilly.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.trilly.dto.post.PostDTO;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.models.Post;
import org.example.trilly.repositories.PostRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponseDTO> getUserPosts(String username){
        Long userId = userRepository.findByUsername(username).getId();
        List<PostResponseDTO> posts = new ArrayList<>();
        postRepository.getAllByUserIdOrderByPostTimeDesc(userId).forEach(p -> {
            posts.add(mapToDTO(p));
        });

        return posts;
    }

    public void addPost(String username, PostDTO postDTO){
        var post = Post.builder()
                .description(postDTO.getDescription())
                .postTime(LocalDateTime.now())
                .user(userRepository.findByUsername(username))
                .place(postDTO.getPlace())
                .mediaUrl(postDTO.getMediaUrl()).build();
        postRepository.save(post);
    }

    private PostResponseDTO mapToDTO(Post p){

        return PostResponseDTO.builder()
                .postId(p.getId())
                .postTime(p.getPostTime())
                .description(p.getDescription())
                .mediaUrl(p.getMediaUrl())
                .username(p.getUser().getUsername())
                .likesCount(getLikesCount( p.getLikes().size()))
                .place(p.getPlace())
                .build();
    }

    private String getLikesCount(Integer likesCount){
        if(likesCount < 1000000)return String.valueOf(likesCount);
        return String.valueOf(likesCount / 1000) + "M";
    }
}
