package org.example.trilly.controllers;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.services.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ChatController{
    private final ChatService chatService;
    @GetMapping("/users/{id}/chats")       //End-point for user CHATS
    public List<ChatResponseDTO> myChats(@PathVariable Long id){
        return chatService.getMyAllChats(id);
    }

    @GetMapping("/chats/{username}")   //End-point for search chat by entering username
    public ChatResponseDTO getChatByUsername(@PathVariable String username){
        return chatService.getChatByUsername(username);
    }


}
