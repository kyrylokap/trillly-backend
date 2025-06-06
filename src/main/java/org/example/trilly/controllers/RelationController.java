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
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowers(@RequestBody RelationRequestDTO request){
        return ResponseEntity.ok(relationService.getUserFollowers(request));
    }

    @GetMapping("/user/followings")
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowings(@RequestBody RelationRequestDTO request){
        return ResponseEntity.ok(relationService.getUserFollowings(request));
    }

    @PutMapping("/user/follow")
    public ResponseEntity<Void> followUser(@RequestBody RelationRequestDTO request){
        relationService.follow(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/unFollow")
    public ResponseEntity<Void> unfollowUser(@RequestBody RelationRequestDTO request){
        relationService.unFollow(request);
        return ResponseEntity.ok().build();
    }
}
