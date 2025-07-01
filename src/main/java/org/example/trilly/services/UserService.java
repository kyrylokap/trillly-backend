package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.post.PostResponseDTO;
import org.example.trilly.dto.user.login.LoginRequestDTO;
import org.example.trilly.dto.user.login.LoginResponseDTO;
import org.example.trilly.dto.user.password.ChangePasswordRequestDTO;
import org.example.trilly.dto.user.password.ChangePasswordResponseDTO;
import org.example.trilly.dto.user.profile.UserProfileDTO;
import org.example.trilly.dto.user.search.UserSearchDTO;
import org.example.trilly.models.Relation;
import org.example.trilly.models.User;
import org.example.trilly.models.enums.RelationStatus;
import org.example.trilly.repositories.PostRepository;
import org.example.trilly.repositories.RelationRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    public LoginResponseDTO registerUser(LoginRequestDTO request){
        if(!userRepository.existsByUsername(request.getUsername())){
            var user = User.builder().role("USER")
                    .password(passwordEncoder.encode(request.getPassword()))
                    .username(request.getUsername())
                    .createdAt(LocalDateTime.now()).build();
            userRepository.save(user);
            return LoginResponseDTO.builder().username(user.getUsername()).build();
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO dto){
        User user = userRepository.findByUsername(dto.getUsername());
        ChangePasswordResponseDTO responseDTO;

        if(dto.getNewPassword().length() < 8){
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Password length must be greater than 8").build();
        }else if (!user.getPassword().equals(dto.getPrevPassword())){
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Bad current password").build();
        } else{
            user.setPassword(dto.getNewPassword());
            responseDTO = ChangePasswordResponseDTO.builder().changePasswordMessage("Successfully changed password!").build();
            userRepository.save(user);
        }
        return responseDTO;
    }
    public String changeUsername(String oldUsername, String newUsername){
        if(oldUsername.equals(newUsername)){
            return "Old and new username are equal, try other username";
        }if(userRepository.findByUsername(newUsername) != null){
            return "User with new username already exists, try other username!";
        }if(newUsername.length() < 3){
            return "Provided username are very small, minimum length is 3";
        }
        var user = userRepository.findByUsername(oldUsername);
        user.setUsername(newUsername);
        userRepository.save(user);

        return oldUsername + " successfully changed username on " + newUsername;
    }

    public UserProfileDTO getUserProfile(String username){
        var userProfile = UserProfileDTO.builder().build();
        userProfile.setUsername(username);
        List<Relation> followers = relationRepository.findRelationsByUsernameAndStatuses(username,
                List.of(RelationStatus.FRIEND, RelationStatus.FOLLOWED));

        userProfile.setFollowersCount(followers.isEmpty() ? 0L: (long)followers.size());
        userProfile.setFollowers(followers.stream().map((x) -> x.getSecondUser().getUsername()).toList());

        List<Relation> followings = relationRepository.findRelationsByUsernameAndStatuses(username,
                List.of(RelationStatus.FRIEND, RelationStatus.FOLLOWING));

        userProfile.setFollowings(followings.stream().map((x) -> x.getFirstUser().getUsername()).toList());
        userProfile.setFollowingsCount( followings.isEmpty() ? 0L:  (long)followings.size());
        userProfile.setPosts(postRepository
                                .getAllByUserIdOrderByPostTimeDesc(userRepository.findByUsername(username).getId())
                                .stream().map(
                                        (x) -> PostResponseDTO.builder()
                                                .postId(x.getId())
                                                .place(x.getPlace()).description(x.getDescription())
                                                .postTime(x.getPostTime())
                                                .likesCount(String.valueOf(x.getLikes().size()))
                                                .mediaUrl(x.getMediaUrl())
                                                .username(username)
                                                .build())
                .toList());
        return userProfile;
    }

    public List<UserSearchDTO>searchAllLikeUsername(String username, String usernameToFind){
        return userRepository.searchAllByUsernameStartingWith(usernameToFind).stream().filter((user ->
                        !relationRepository.isBlocked(username, user.getUsername(), RelationStatus.BLOCKED)
        ))
                .map((user -> UserSearchDTO.builder().username(user.getUsername()).build()
        )).toList();
    }
}
