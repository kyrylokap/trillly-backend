package org.example.trilly.controllers;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ChatController{
    private final ChatService chatService;
    @GetMapping("/users/user/chats")
    public ResponseEntity<List<ChatResponseDTO>> getMyChats(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(chatService.getMyAllChats(username));
    }
    @GetMapping("/chats/user/{foundUsername}")
    public ResponseEntity<List<ChatResponseDTO>> getChatBySearching(@PathVariable String foundUsername){
        String finderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(chatService.getChatsByUsername(finderUsername, foundUsername));
    }

    @GetMapping("/chats/find")
    public ResponseEntity<ChatResponseDTO> findChatById(@RequestParam Long id){
        return ResponseEntity.ok(chatService.getChatById(id));
    }
    @GetMapping("/users/getChat")
    public ResponseEntity<ChatResponseDTO> createChat(@RequestParam String secondUsername){
        String firstUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(chatService.createAndGetChat(firstUsername, secondUsername));
    }


}
