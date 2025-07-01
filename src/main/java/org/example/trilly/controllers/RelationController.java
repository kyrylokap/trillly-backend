package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.relation.RelationRequestDTO;
import org.example.trilly.dto.relation.RelationResponseDTO;
import org.example.trilly.services.RelationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Void> followUser(@RequestParam String secondUsername){
        String firstUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        relationService.follow(firstUsername, secondUsername);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/unFollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam String secondUsername){
        String firstUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        relationService.unfollow(firstUsername, secondUsername);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/checkFollow")
    public ResponseEntity<Boolean> checkFollow(@RequestParam String secondUsername){
        String firstUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(relationService.checkFollow(firstUsername, secondUsername));
    }

    @PutMapping("/user/block")
    public ResponseEntity<Void> blockUser(@RequestParam String profileUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        relationService.block(username,profileUsername);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/unblock")
    public ResponseEntity<Void> unblockUser(@RequestParam String profileUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        relationService.unblock(username,profileUsername);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/checkBlock")
    public ResponseEntity<Boolean> getBlockStatus(@RequestParam String profileUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(relationService.isBlocked(username,profileUsername));
    }
}
