package org.example.trilly.dto.user.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trilly.dto.post.PostResponseDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private String username;
    private List<String> followers;
    private List<String> followings;
    private Long followersCount;
    private Long followingsCount;
    private List<PostResponseDTO> posts;

}
