package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.repositories.ChatRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;


}
