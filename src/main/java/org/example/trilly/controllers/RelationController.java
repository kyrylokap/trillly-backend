package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
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
    @GetMapping("/{username}/followers")
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowers(@PathVariable String username){
        return ResponseEntity.ok(relationService.getUserFollowers(username));
    }

    @GetMapping("/{username}/followings")
    public ResponseEntity<List<RelationResponseDTO>> getUserFollowings(@PathVariable String username){
        return ResponseEntity.ok(relationService.getUserFollowings(username));
    }

}
