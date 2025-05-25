package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessageResponseDTO;
import org.example.trilly.services.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class MessageController{
    private MessageService messageService;
    @GetMapping("/chats/{chatId}/messages")
    public MessageResponseDTO messagesByChatId(@PathVariable Long chatId){
        return messageService.getMessagesByChatId(chatId);
    }
}