package org.example.trilly.controllers;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ChatController{
    private final ChatService chatService;
    @GetMapping("/users/{username}/chats")       //End-point for user CHATS
    public ResponseEntity<List<ChatResponseDTO>> getMyChats(@PathVariable String username){
        return ResponseEntity.ok(chatService.getMyAllChats(username));
    }
    @GetMapping("/chats/{finderUsername}/{foundUsername}")   //End-point for search chat by entering username
    public ResponseEntity<List<ChatResponseDTO>> getChatBySearching(@PathVariable String finderUsername,
                                             @PathVariable String foundUsername){
        return ResponseEntity.ok(chatService.getChatsByUsername(finderUsername, foundUsername));
    }

    @GetMapping("/chats/find")
    public ResponseEntity<ChatResponseDTO> findChatById(@RequestParam Long id){
        return ResponseEntity.ok(chatService.getChatById(id));
    }
    @GetMapping("/users/getChat")
    public ResponseEntity<ChatResponseDTO> createChat(@RequestParam String firstUsername,
                                                      @RequestParam String secondUsername){
        return ResponseEntity.ok(chatService.createAndGetChat(firstUsername, secondUsername));
    }


}
