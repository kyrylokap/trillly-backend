package org.example.trilly.service;

import org.example.trilly.repositories.LikeRepository;
import org.example.trilly.repositories.PostRepository;
import org.example.trilly.repositories.UserRepository;
import org.example.trilly.services.LikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @InjectMocks
    private LikeService likeService;


    @Test

    void testLikePost(){

    }
}
