package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.relation.RelationRequestDTO;
import org.example.trilly.dto.relation.RelationResponseDTO;
import org.example.trilly.services.RelationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class RelationController{
    private final RelationService relationService;
    @GetMapping("/user/followers")
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowers(@RequestParam String username){
        return ResponseEntity.ok(relationService.getUserFollowers(username));
    }

    @GetMapping("/user/followings")
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowings(@RequestParam String username){
        return ResponseEntity.ok(relationService.getUserFollowings(username));
    }

    @PutMapping("/user/follow")
    public ResponseEntity<Void> followUser(@RequestBody RelationRequestDTO request){
        relationService.follow(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/unFollow")
    public ResponseEntity<Void> unfollowUser(@RequestBody RelationRequestDTO request){
        relationService.unfollow(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/block")
    public ResponseEntity<Void> blockUser(@RequestParam String username,
                                          @RequestParam String profileUsername){
        relationService.block(username,profileUsername);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/unblock")
    public ResponseEntity<Void> unblockUser(@RequestBody RelationRequestDTO request){
        relationService.unblock(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/checkFollow")
    public ResponseEntity<Boolean> checkFollow(@RequestParam String firstUsername,
                                               @RequestParam String secondUsername){
        return ResponseEntity.ok(relationService.checkFollow(firstUsername, secondUsername));
    }
}
