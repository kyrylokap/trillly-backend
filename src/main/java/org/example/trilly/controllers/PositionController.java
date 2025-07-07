package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.position.PositionDTO;
import org.example.trilly.services.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PositionController {
    private final PositionService positionService;

    @PostMapping("/user/addPosition")
    public ResponseEntity<?> addPositionToUser(@RequestParam Double longitude,
                                               @RequestParam Double latitude){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        positionService.addPositionToUser(username,longitude, latitude);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/user/positions/today")
    public ResponseEntity<List<PositionDTO>> getPosToday(@RequestParam String username){
        return ResponseEntity.ok(positionService.posesToday(username));
    }


}
