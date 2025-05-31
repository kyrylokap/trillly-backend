package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.repositories.LikeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    //TODO CREATE DTO FOR LIKES
}
