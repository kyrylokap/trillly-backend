package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.models.Like;
import org.example.trilly.models.Post;
import org.example.trilly.repositories.LikeRepository;
import org.example.trilly.repositories.PostRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponseDTO> getMyLikes(String username){
        var userId = userRepository.findByUsername(username).getId();
        List<PostResponseDTO> myLikes = new ArrayList<>();
        likeRepository.getAllByUserId(userId).forEach(like -> {
            myLikes.add(mapToDTO(like.getPost()));
        });
        return myLikes;
    }
    private PostResponseDTO mapToDTO(Post p){

        return PostResponseDTO.builder()
                .postId(p.getId())
                .postTime(p.getPostTime())
                .description(p.getDescription())
                .mediaUrl(p.getMediaUrl())
                .username(p.getUser().getUsername())
                .likesCount(getLikesCount( p.getLikes().size()))
                .build();
    }
    private String getLikesCount(Integer likesCount){
        if(likesCount < 1000000)return String.valueOf(likesCount);
        return String.valueOf(likesCount / 1000) + "M";
    }

    public boolean likePost(String username, Long postId){
        if(likeRepository.existsByUserUsernameAndPostId(username,postId)) {
            Like l = likeRepository.getFirstByUserUsernameAndPostId(username, postId);
            likeRepository.delete(l);
            return false;
        }
        var user = userRepository.findByUsername(username);
        var post = postRepository.getReferenceById(postId);
        likeRepository.save(Like.builder().post(post).user(user).build());

        return true;
    }
    public boolean isLikedByUsernameAndPostId(String username, Long postId){
        return likeRepository.existsByUserUsernameAndPostId(username, postId);
    }
}
